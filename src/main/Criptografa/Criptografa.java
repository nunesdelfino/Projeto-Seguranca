package main.Criptografa;

import main.GeraHash.GeraHashArquivo;
import main.GerarParDeChave.GerarChaves;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class Criptografa {

    public static final String CAMINHO_MENSAGEM_CRIPTOGRAFADA = "Mensagem/criptografada.msg";

    public static final String CAMINHO_ARQUIVO_HASH = "Arquivo.pdf";

    /**
     * Criptografa o texto puro usando chave pública.
     */
    public static byte[] criptografa(String texto, PublicKey chave) {
        byte[] cipherText = null;

        try {

            final Cipher cipher = Cipher.getInstance(GerarChaves.ALGORITMO);

            // Criptografa o texto puro usando a chave Púlica
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            cipherText = cipher.doFinal(texto.getBytes());


        } catch (Exception e) {
            e.printStackTrace();
        }

        return cipherText;
    }

    public static String Hash() throws IOException, NoSuchAlgorithmException {

        File file = new File(CAMINHO_ARQUIVO_HASH);

        //SHA-1 checksum
        String shaChecksum = GeraHashArquivo.getFileChecksum(file);

        return shaChecksum;

    }

    public static void main(String[] args){

        try {



            ObjectInputStream inputStream = null;

            if (!GerarChaves.verificaSeExisteChavesNoSO()) {
                // Método responsável por gerar um par de chaves usando o algoritmo RSA e
                // armazena as chaves nos seus respectivos arquivos.
                GerarChaves.geraChave();
            }

            // Criptografa a Mensagem usando a Chave Pública
            inputStream = new ObjectInputStream(new FileInputStream(GerarChaves.CAMINHO_CHAVE_PUBLICA));
            final PublicKey chavePublica = (PublicKey) inputStream.readObject();
            final byte[] textoCriptografado = Criptografa.criptografa(Hash(), chavePublica);

            File mensagemCriptografada = new File(CAMINHO_MENSAGEM_CRIPTOGRAFADA);
            if (mensagemCriptografada.getParentFile() != null) {
                mensagemCriptografada.getParentFile().mkdirs();
            }
            mensagemCriptografada.createNewFile();

            String t = new String(textoCriptografado, StandardCharsets.UTF_8);

            ObjectOutputStream mensagem = new ObjectOutputStream(
                    new FileOutputStream(mensagemCriptografada));
            mensagem.writeObject(t);
            mensagem.close();

            System.out.println("Mensagem criptografada e salva no caminho: " + CAMINHO_MENSAGEM_CRIPTOGRAFADA);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
