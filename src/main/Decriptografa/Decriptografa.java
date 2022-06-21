package main.Decriptografa;

import main.GerarParDeChave.GerarChaves;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.security.PrivateKey;

public class Decriptografa {

    /**
     * Decriptografa o texto puro usando chave privada.
     */
    public static String decriptografa(byte[] texto, PrivateKey chave) {
        byte[] dectyptedText = null;

        try {
            final Cipher cipher = Cipher.getInstance(GerarChaves.ALGORITMO);
            // Decriptografa o texto puro usando a chave Privada
            cipher.init(Cipher.DECRYPT_MODE, chave);
            dectyptedText = cipher.doFinal(texto);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(dectyptedText);
    }

    public static final String CAMINHO_MENSAGEM_CRIPTOGRAFADA = "Mensagem/criptografada.msg";

    public static void main(String[] args) {

        try {

            ObjectInputStream inputStream = null;

            ObjectInputStream mensagemSaida = null;

            mensagemSaida = new ObjectInputStream(new FileInputStream(CAMINHO_MENSAGEM_CRIPTOGRAFADA));
            final byte[] mensagemTexto = (byte[]) mensagemSaida.readObject();

            // Decriptografa a Mensagem usando a Chave Pirvada
            inputStream = new ObjectInputStream(new FileInputStream(GerarChaves.CAMINHO_CHAVE_PRIVADA));
            final PrivateKey chavePrivada = (PrivateKey) inputStream.readObject();
            final String textoPuro = Decriptografa.decriptografa(mensagemTexto, chavePrivada);

            System.out.println(textoPuro);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
