package com.cy.util.keystore;

import com.cy.util.security.DigestUtil;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.configuration.ConfigurationUtils;
import org.apache.commons.configuration.FileSystem;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.List;

import static com.cy.util.keystore.KeyStoreUtil.loadKeyStore;

/**
 * Created by leo on 17-2-13.
 */
public class CertUtilTest {

    private String testTrustStorePwd = "xiaomi";

    private String testTrustStore = "spTrust.keystore";

    @Test
    public void getKeyCert() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        URL url = ConfigurationUtils.locate(FileSystem.getDefaultFileSystem(), null, testTrustStore);
        KeyStore keyStore = loadKeyStore(url.getFile(), testTrustStorePwd);
//        List<Certificate> certificateList = CertUtil.getKeyCert(keyStore);
//        for (Certificate certificate : certificateList) {
//            System.out.println(certificate.getPublicKey());
//        }
//        System.out.println(certificateList.size());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        keyStore.store(byteArrayOutputStream, testTrustStorePwd.toCharArray());
        byte[] certificateByte = CertUtil.deleteCert(byteArrayOutputStream.toByteArray(), testTrustStorePwd, "");

        KeyStore keyStore1 = KeyKeyStoreUtil.loadKeyStore(certificateByte, testTrustStorePwd);
//        List<Certificate> certificateList = CertUtil.getKeyCert(keyStore1);
//        for (Certificate certificate : certificateList) {
//            System.out.println(certificate);
//        }
//        System.out.println(certificateList.size());
        //ByteArrayInputStream keyStoreInputStream = new ByteArrayInputStream(null);
            //System.out.println("before---" + get());
        //System.out.println("---" + keyStoreInputStream);

        System.out.println(Integer.parseInt("222"));
    }

    private ByteArrayOutputStream get() throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException {
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            URL url = ConfigurationUtils.locate(FileSystem.getDefaultFileSystem(), null, testTrustStore);
            KeyStore keyStore = loadKeyStore(url.getFile(), testTrustStorePwd);
            //byteArrayOutputStream = new ByteArrayOutputStream();
            keyStore.store(byteArrayOutputStream, testTrustStorePwd.toCharArray());
        } finally {
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
        }

        return byteArrayOutputStream;
    }
}
