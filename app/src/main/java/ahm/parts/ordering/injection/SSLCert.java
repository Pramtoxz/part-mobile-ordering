package ahm.parts.ordering.injection;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okio.Buffer;

public final class SSLCert {

    OkHttpClient.Builder CustomTrust(OkHttpClient.Builder client) {
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
            client.sslSocketFactory(sslSocketFactory, trustManager);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    /**
     * Returns an input stream containing one or more certificate PEM files. This implementation just
     * embeds the PEM files in Java strings; most applications will instead read this from a resource
     * file that gets bundled with the application.
     */
    private InputStream trustedCertificatesInputStream() {
        String comodoRsaCertificationAuthority = ""
                + "-----BEGIN CERTIFICATE-----\n" +
                "MIIFUTCCBDmgAwIBAgISA4UtR5OjXa40sEPNJDTGL/o0MA0GCSqGSIb3DQEBCwUA\n" +
                "MEoxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MSMwIQYDVQQD\n" +
                "ExpMZXQncyBFbmNyeXB0IEF1dGhvcml0eSBYMzAeFw0yMDA1MDMyMzAyMjFaFw0y\n" +
                "MDA4MDEyMzAyMjFaMBcxFTATBgNVBAMTDGFucGVyc3lzLmNvbTCCASIwDQYJKoZI\n" +
                "hvcNAQEBBQADggEPADCCAQoCggEBALI6fUCuOm+yJrJeyn/yXVqqSXlCUga0MlFf\n" +
                "BDlENuSoe2SgqMos6TRovoOcgvf9UZkR6wYKO1Ul4rpXa4iUnokqxSa/z7vO3UrN\n" +
                "9gR83viY7Y1IQr7aIkRUXabtOMBS2m2jqV7r+stVytebSHxcGP2yKpPTbJESave8\n" +
                "gF0N7zfcwmu4OTr4SqRqlSbpBCVbmOp3VUjH4wZa9zRZxZjqEWzzMooXAeBy2ssW\n" +
                "EglD3Pcv8wKDgFChpve4e3Yqeog2TsVQ/oqr9bZaHHo2+8euNiyHZpFrveN5Ynkh\n" +
                "0v6yrpj1Ump+olQfu3XH5vLJV8l30J1ixWoXFmZdUAeXpEY/DC8CAwEAAaOCAmIw\n" +
                "ggJeMA4GA1UdDwEB/wQEAwIFoDAdBgNVHSUEFjAUBggrBgEFBQcDAQYIKwYBBQUH\n" +
                "AwIwDAYDVR0TAQH/BAIwADAdBgNVHQ4EFgQUVOj/uTVHczDRRFLqj40El9s9A8Yw\n" +
                "HwYDVR0jBBgwFoAUqEpqYwR93brm0Tm3pkVl7/Oo7KEwbwYIKwYBBQUHAQEEYzBh\n" +
                "MC4GCCsGAQUFBzABhiJodHRwOi8vb2NzcC5pbnQteDMubGV0c2VuY3J5cHQub3Jn\n" +
                "MC8GCCsGAQUFBzAChiNodHRwOi8vY2VydC5pbnQteDMubGV0c2VuY3J5cHQub3Jn\n" +
                "LzAXBgNVHREEEDAOggxhbnBlcnN5cy5jb20wTAYDVR0gBEUwQzAIBgZngQwBAgEw\n" +
                "NwYLKwYBBAGC3xMBAQEwKDAmBggrBgEFBQcCARYaaHR0cDovL2Nwcy5sZXRzZW5j\n" +
                "cnlwdC5vcmcwggEFBgorBgEEAdZ5AgQCBIH2BIHzAPEAdgBep3P531bA57U2SH3Q\n" +
                "SeAyepGaDIShEhKEGHWWgXFFWAAAAXHc/aBeAAAEAwBHMEUCIAQg9HLsceQIM+V1\n" +
                "WSJMz3CPC+ZRoS887ZXOaHcWVwz9AiEAxLcJFIofaxyw9UEoOtkittW/Q+d6u695\n" +
                "c1ollfJyz4YAdwAHt1wb5X1o//Gwxh0jFce65ld8V5S3au68YToaadOiHAAAAXHc\n" +
                "/aB+AAAEAwBIMEYCIQCs0Ecrm8QidRTZsmfnuHGA/6OZ6E5auR/OGXnxzk0c5AIh\n" +
                "AJtuZkng61JAQn5X9q/Oslui5VhJwFAULElGYrQAdFTZMA0GCSqGSIb3DQEBCwUA\n" +
                "A4IBAQB/2NPKo955AzRbrCJIFisC5xj4X3JbPUHj9fZ2wO6ZPdOdBlPnYWar74p3\n" +
                "5ZIX+WSWoyLtx2pi913s0CJcKl32/b7hMLdNK0bIa05GD/e6PxN4YkNCBt51HKoB\n" +
                "Oh+HDTeuhWY0JdlKUST2+pTNKkyBP2ZZpx2wiD5a/xVCZKcPujbvjAZ9G0kX6oN4\n" +
                "3Faadz9+Zv4DAnv2wN9RIP34nD8LqIF4Y7sw0OwFdlm5vFX2t+JDusSHY4tTgjux\n" +
                "/3yeZHB+eO+a/pjlg6ZgpBLH8QbpGyaT1Puwhw2GHTKbsgvuWtMikTw5opQFA514\n" +
                "Nn9+wHPdF+ETXwB4n/kuMeN6oWDx\n" +
                "-----END CERTIFICATE-----\n";
        return new Buffer()
                .writeUtf8(comodoRsaCertificationAuthority)
                .inputStream();
    }

    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     *
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     *
     * <p>See also {@link CertificatePinner}, which can limit trusted certificates while still using
     * the host platform's built-in trust store.
     *
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     *
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

}