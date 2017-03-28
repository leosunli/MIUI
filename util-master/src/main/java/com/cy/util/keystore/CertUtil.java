package com.cy.util.keystore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CertUtil {
    private static final String CERTIFICATE_FORMAT = "X509";

    private static final String SHA1 = "SHA1";

    private static final String MD5 = "MD5";

    public static X509Certificate parseCert(byte[] content) throws IOException, CertificateException {
        if (content == null) {
            throw new IllegalArgumentException("null content");
        }
        ByteArrayInputStream in = null;
        try {
            in = new ByteArrayInputStream(content);
            CertificateFactory certificateFactory = CertificateFactory.getInstance(CERTIFICATE_FORMAT);
            return (X509Certificate) certificateFactory.generateCertificate(in);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public static byte[] getCertSha1(X509Certificate certificate) throws NoSuchAlgorithmException, CertificateEncodingException {
        if (certificate == null) {
            throw new IllegalArgumentException("null certificate");
        }
        MessageDigest digest = MessageDigest.getInstance(SHA1);
        digest.update(certificate.getEncoded());
        return digest.digest();
    }

    public static byte[] getCertMD5(X509Certificate certificate) throws NoSuchAlgorithmException, CertificateEncodingException {
        if (certificate == null) {
            throw new IllegalArgumentException("null certificate");
        }
        MessageDigest digest = MessageDigest.getInstance(MD5);
        digest.update(certificate.getEncoded());
        return digest.digest();
    }

    public static List<Certificate> getCertList(KeyStore keyStore) throws KeyStoreException {
        if (keyStore == null) {
            throw new IllegalArgumentException("null keyStore");
        }
        List<Certificate> certificateList = new ArrayList<Certificate>();
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
            certificateList.add(certificate);
        }
        return certificateList;
    }

    public static byte[] deleteCert(byte[] keyStore, String pwd, String certSha1) throws CertificateException,
            NoSuchAlgorithmException, KeyStoreException, IOException {
        ByteArrayOutputStream keyStoreOutputStream = null;
        byte[] keyStoreResult = null;
        try {
            KeyStore ks = KeyStoreUtil.loadKeyStore(keyStore, pwd);
            List<Certificate> certificateList = getCertList(ks);
            for (Certificate certificate : certificateList) {
                X509Certificate x509Certificate = (X509Certificate) certificate;
                String certificateSha1 = new String(getCertSha1(x509Certificate));
                if (certificateSha1.equals(certSha1)) {
                    System.out.println("---" + certSha1);
                    ks.deleteEntry(x509Certificate.getSignature().toString());
                }
            }
            keyStoreOutputStream = new ByteArrayOutputStream();
            ks.store(keyStoreOutputStream, pwd.toCharArray());
            keyStoreResult = keyStoreOutputStream.toByteArray();
        } finally {
            if (keyStoreOutputStream != null) {
                keyStoreOutputStream.close();
            }
        }
        return keyStoreResult;
    }

    public static byte[] addCert(byte[] keyStore, String pwd, byte[] certificate) throws CertificateException,
            NoSuchAlgorithmException, KeyStoreException, IOException {
        ByteArrayOutputStream keyStoreOutputStream = null;
        byte[] keyStoreResult = null;
        try {
            KeyStore ks = KeyStoreUtil.loadKeyStore(keyStore, pwd);
            X509Certificate x509Certificate = parseCert(certificate);
            ks.setCertificateEntry(x509Certificate.getSignature().toString(), x509Certificate);
            keyStoreOutputStream = new ByteArrayOutputStream();
            ks.store(keyStoreOutputStream, pwd.toCharArray());
            keyStoreResult = keyStoreOutputStream.toByteArray();
        } finally {
            if (keyStoreOutputStream != null) {
                keyStoreOutputStream.close();
            }
        }
        return keyStoreResult;
    }

    public static KeyStore addCert(KeyStore keyStore, byte[] certificate) throws CertificateException,
            NoSuchAlgorithmException, KeyStoreException, IOException {
        if (keyStore == null) {
            throw new IllegalArgumentException("null keyStore");
        }
        X509Certificate x509Certificate = parseCert(certificate);
        keyStore.setCertificateEntry(x509Certificate.getSignature().toString(), x509Certificate);
        return keyStore;
    }
}
