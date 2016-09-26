package sample.sd;

import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.jcajce.JcaPGPObjectFactory;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePGPDataEncryptorBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyDataDecryptorFactoryBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePublicKeyKeyEncryptionMethodGenerator;
import org.bouncycastle.util.io.Streams;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Date;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

public class PublicKeyTest {

    @Test
    public void testEncrypt() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FileOutputStream fos = new FileOutputStream("/tmp/test.asc");
        encryptFile(getLiteralBytes("hello world !!!".getBytes()), out, readPublicKey(getClass().getResourceAsStream("/epp_tsig.asc")), true, true);
        fos.write(out.toByteArray());
        fos.close();
    }

    @Test
    public void testEncryptDecrypt() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        ByteArrayOutputStream encrypt = new ByteArrayOutputStream();
        encryptFile(getLiteralBytes("hello world !!!".getBytes()), encrypt, readPublicKey(getClass().getResourceAsStream("/epp_tsig.asc")), true, true);

        ByteArrayOutputStream decrypt = new ByteArrayOutputStream();
        decryptFile(new ByteArrayInputStream(encrypt.toByteArray()), getClass().getResourceAsStream("/epp_tsig.gpg"), "PzJaKPcBrKSb_ivXybD0svaREXQZ5x7".toCharArray(), decrypt);

        assertThat(decrypt.toString()).isEqualTo("hello world !!!");
    }

    @Test
    public void testDecryptFileFromGPG() throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        String gpgv2 = "-----BEGIN PGP MESSAGE-----\n" +
                "Version: GnuPG v2\n" +
                "\n" +
                "hQIMAxMDycAue2R1ARAAmymzWh/kUTTkwdC3wwD+7S9xOa8hXPH2wWlPC4zpvPxj\n" +
                "JdxHIuUp3TAcENDSzEk7TO9gYJSXuYreNPV1h783R7tKZuhPa6/RyF8x9k1mdgoZ\n" +
                "erGBPI7yBHHGOFeMbPS+updlYu8VWGnqoEQisMTTKfoTDpl+K17s0Dh2bI62iJSb\n" +
                "i8rZQSM1QWfeWJSxpBtJuge37XsKl8lTWvo+4t5htYtDIjNmH33M+BFXQwER1Ik9\n" +
                "YRMxDWnkWOTX88F0AM77eerGudFMyGnJP5PG/aXmGNsKSxSqODS4Ici/bdN7u2XZ\n" +
                "rM/rzkCXTBydVcvefOj2Jsp3Dd6xZLx4a4PeSwVf0vZ/ZeGaABllIJpwAmpfYdKA\n" +
                "2hlmUSFy8EsdG2nrHhcTx37hKSxDxR+eEf35UISma+tgEu9puMdA+4TmWL4HuRqq\n" +
                "HUW0DK8FGZ9b7DuJXbWO7RfIOPeMNG2btQ852FWt3gcxXWC+IL+mCJsg2fjfEqD0\n" +
                "L78DfB59vE3b8TYMZ2GHtpFC5CoLrzpQyspmsVp9r65YbH6X4MO8LHcaO9gxAa9E\n" +
                "n9DDEleblziP8mHiYH5ikJ74byJ9lxKGps6XpRUxTONNJRFkQyHK7/w6LoR4O2CK\n" +
                "HKePLEZtrMrqw+p3Ji42gZiQGextY0mvmalDW98IH8OZwZgG5TCMgeMAHyL08ArS\n" +
                "TQGcXFk3Urg34N8/mbJ2E8nt2mFZH1nPyAOfWKJHbbrfTlqy9hKgHUypD0nIgq53\n" +
                "nXFQjcbfF3IgQ79Bb9W09EH0WdQU3zsya07fjepd\n" +
                "=I+q7\n" +
                "-----END PGP MESSAGE-----";
        ByteArrayOutputStream decrypt = new ByteArrayOutputStream();
        decryptFile(new ByteArrayInputStream(gpgv2.getBytes()), getClass().getResourceAsStream("/epp_tsig.gpg"), "PzJaKPcBrKSb_ivXybD0svaREXQZ5x7".toCharArray(), decrypt);
        assertThat(decrypt.toString()).isEqualTo("hello world\n");
    }



    private byte[] getLiteralBytes(byte[] rawData) throws IOException {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        PGPLiteralDataGenerator literalDataGen = new PGPLiteralDataGenerator();

        OutputStream literalOutStream = literalDataGen.open(bOut,
                PGPLiteralData.BINARY,
                "",
                rawData.length,
                new Date(1L));
        literalOutStream.write(rawData);
        literalOutStream.close();

        return bOut.toByteArray();

    }

    /**
     * A simple routine that opens a key ring file and loads the first available key
     * suitable for encryption.
     *
     * @param input data stream containing the public key data
     * @return the first public key found.
     * @throws IOException
     * @throws PGPException
     */
    static PGPPublicKey readPublicKey(InputStream input) throws IOException, PGPException {
        PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(
                PGPUtil.getDecoderStream(input), new JcaKeyFingerprintCalculator());

        //
        // we just loop through the collection till we find a key suitable for encryption, in the real
        // world you would probably want to be a bit smarter about this.
        //

        Iterator keyRingIter = pgpPub.getKeyRings();
        while (keyRingIter.hasNext()) {
            PGPPublicKeyRing keyRing = (PGPPublicKeyRing) keyRingIter.next();

            Iterator keyIter = keyRing.getPublicKeys();
            while (keyIter.hasNext()) {
                PGPPublicKey key = (PGPPublicKey) keyIter.next();

                if (key.isEncryptionKey()) {
                    return key;
                }
            }
        }

        throw new IllegalArgumentException("Can't find encryption key in key ring.");
    }

    private static void encryptFile(
            byte[] bytes, OutputStream out,
            PGPPublicKey encKey,
            boolean armor,
            boolean withIntegrityCheck)
            throws IOException, NoSuchProviderException {
        if (armor) {
            out = new ArmoredOutputStream(out);
        }

        try {
            System.out.println(encKey.getAlgorithm());
            //SecureRandom random = SecureRandom.getInstanceStrong();
            SecureRandom random = new SecureRandom();
            random.nextBytes(new byte[20]);
            PGPEncryptedDataGenerator encGen = new PGPEncryptedDataGenerator(
                    new JcePGPDataEncryptorBuilder(PGPEncryptedData.CAST5).setWithIntegrityPacket(withIntegrityCheck).setSecureRandom(random).setProvider("BC"));

            encGen.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(encKey).setProvider("BC"));

            OutputStream cOut = encGen.open(out, bytes.length);
            cOut.write(bytes);
            cOut.close();

            if (armor) {
                out.close();
            }
        } catch (PGPException e) {
            System.err.println(e);
            if (e.getUnderlyingException() != null) {
                e.getUnderlyingException().printStackTrace();
            }
        }
    }

    /**
     * decrypt the passed in message stream
     */
    private static void decryptFile(
            InputStream in,
            InputStream keyIn,
            char[]      passwd,
            OutputStream out)
            throws IOException, NoSuchProviderException
    {
        in = PGPUtil.getDecoderStream(in);

        try
        {
            JcaPGPObjectFactory pgpF = new JcaPGPObjectFactory(in);
            PGPEncryptedDataList enc;

            Object                  o = pgpF.nextObject();
            //
            // the first object might be a PGP marker packet.
            //
            if (o instanceof PGPEncryptedDataList)
            {
                enc = (PGPEncryptedDataList)o;
            }
            else
            {
                enc = (PGPEncryptedDataList)pgpF.nextObject();
            }

            //
            // find the secret key
            //
            Iterator                    it = enc.getEncryptedDataObjects();
            PGPPrivateKey sKey = null;
            PGPPublicKeyEncryptedData pbe = null;
            PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
                    PGPUtil.getDecoderStream(keyIn), new JcaKeyFingerprintCalculator());

            while (sKey == null && it.hasNext())
            {
                pbe = (PGPPublicKeyEncryptedData)it.next();

                sKey = findSecretKey(pgpSec, pbe.getKeyID(), passwd);
            }

            if (sKey == null)
            {
                throw new IllegalArgumentException("secret key for message not found.");
            }

            InputStream         clear = pbe.getDataStream(new JcePublicKeyDataDecryptorFactoryBuilder().setProvider("BC").build(sKey));

            JcaPGPObjectFactory    plainFact = new JcaPGPObjectFactory(clear);

            Object              message = plainFact.nextObject();

            if (message instanceof PGPCompressedData)
            {
                PGPCompressedData   cData = (PGPCompressedData)message;
                JcaPGPObjectFactory    pgpFact = new JcaPGPObjectFactory(cData.getDataStream());

                message = pgpFact.nextObject();
            }

            if (message instanceof PGPLiteralData)
            {
                PGPLiteralData ld = (PGPLiteralData)message;
                InputStream unc = ld.getInputStream();
                //OutputStream fOut = new BufferedOutputStream(new FileOutputStream(outFileName));
                Streams.pipeAll(unc, out);
                out.close();
            }
            else if (message instanceof PGPOnePassSignatureList)
            {
                throw new PGPException("encrypted message contains a signed message - not literal data.");
            }
            else
            {
                throw new PGPException("message is not a simple encrypted file - type unknown.");
            }

            if (pbe.isIntegrityProtected())
            {
                if (!pbe.verify())
                {
                    System.err.println("message failed integrity check");
                }
                else
                {
                    System.err.println("message integrity check passed");
                }
            }
            else
            {
                System.err.println("no message integrity check");
            }
        }
        catch (PGPException e)
        {
            System.err.println(e);
            if (e.getUnderlyingException() != null)
            {
                e.getUnderlyingException().printStackTrace();
            }
        }
    }

    /**
     * Search a secret key ring collection for a secret key corresponding to keyID if it
     * exists.
     *
     * @param pgpSec a secret key ring collection.
     * @param keyID keyID we want.
     * @param pass passphrase to decrypt secret key with.
     * @return the private key.
     * @throws PGPException
     * @throws NoSuchProviderException
     */
    static PGPPrivateKey findSecretKey(PGPSecretKeyRingCollection pgpSec, long keyID, char[] pass)
            throws PGPException, NoSuchProviderException
    {
        PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);

        if (pgpSecKey == null)
        {
            return null;
        }

        return pgpSecKey.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().setProvider("BC").build(pass));
    }


}
