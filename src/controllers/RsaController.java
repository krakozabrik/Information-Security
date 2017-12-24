package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.CharSet;
import org.apache.commons.lang3.StringUtils;
import sun.security.rsa.RSAPrivateKeyImpl;
import sun.security.rsa.RSAPublicKeyImpl;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import static org.apache.commons.lang3.StringUtils.join;

public class RsaController {

    @FXML
    TextArea RSA_ORIGINAL;

    @FXML
    TextArea RSA_CIPHER;

    @FXML
    TextField RSA_PUBLIC_KEY;

    @FXML
    TextField RSA_PRIVATE_KEY;

    @FXML
    Button RSA_ENCRYPT;

    @FXML
    Button RSA_GENERATE;

    @FXML
    Button RSA_DECIPHER;

    KeyPair keyPair;

    public void initialize() {
        RSA_GENERATE.setOnAction(event -> {
            try {
                generateOnAction(event);
            } catch (NoSuchAlgorithmException ignored) {
                ignored.printStackTrace();
            }
        });

        RSA_ENCRYPT.setOnAction(event -> {
            try {
                encryptOnAction(event);
            } catch (Exception ignored){
                ignored.printStackTrace();
            }
        });

        RSA_DECIPHER.setOnAction(event ->{
            try {
                decipherOnAction(event);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        });
    }

    private void generateOnAction(ActionEvent event) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(512);

        keyPair = keyGen.genKeyPair();
        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey = keyPair.getPrivate().getEncoded();
        StringBuilder pubString = new StringBuilder();
        StringBuilder privString = new StringBuilder();
        for (byte a : publicKey) {
            pubString.append(Integer.toHexString(0x0100 + (a & 0x00FF)).substring(1));
        }
        for (byte b : privateKey) {
            privString.append(Integer.toHexString(0x0100 + (b & 0x00FF)).substring(1));
        }
        RSA_PUBLIC_KEY.setText(pubString.toString());
        RSA_PRIVATE_KEY.setText(privString.toString());
    }

    private void encryptOnAction(ActionEvent event) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        if(RSA_ORIGINAL.getText().length() == 0){
            return;
        }
        Cipher cipher = Cipher.getInstance("RSA");

        String key = RSA_PUBLIC_KEY.getText();

        byte[] keyData = DatatypeConverter.parseHexBinary(key);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        RSAPublicKey publicKey = (RSAPublicKey) factory
                .generatePublic(new X509EncodedKeySpec(keyData));

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        RSA_CIPHER.setText(
                Base64.getEncoder().encodeToString(
                        cipher.doFinal(
                                RSA_ORIGINAL
                                        .getText()
                                        .getBytes("UTF-8")
                        )
                )
        );
    }

    private void decipherOnAction(ActionEvent event) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance("RSA");

        String key = RSA_PRIVATE_KEY.getText();
        byte[] keyData = DatatypeConverter.parseHexBinary(key);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        RSAPrivateKey privateKey = (RSAPrivateKey) factory
                .generatePrivate(new PKCS8EncodedKeySpec(keyData));

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        RSA_ORIGINAL.setText(new String(cipher.doFinal(Base64.getDecoder().decode(RSA_CIPHER.getText()))));
    }


}
