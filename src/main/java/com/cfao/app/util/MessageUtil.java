package com.cfao.app.util;

/**
 * Criação de mensagem apartir do classe de dialogo
 */
public class MessageUtil {

    private MessageUtil() {
    }

    public static void info(String mensagem) {
        DialogUtil.mensagens("INFO", "Informação", mensagem);
    }

    public static void info(String mensagem, String titulo) {
        DialogUtil.mensagens("INFO", titulo, mensagem);
    }

    public static void erro(String mensagem) {
        DialogUtil.mensagens("ERRO", "Erro", mensagem);
    }

    public static void erro(String mensagem, String titulo) {
        DialogUtil.mensagens("ERRO", titulo, mensagem);
    }

    public static void alerta(String mensagem) {
        DialogUtil.mensagens("ALERTA", "Alerta", mensagem);
    }

    public static void alerta(String mensagem, String titulo) {
        DialogUtil.mensagens("ALERTA", titulo, mensagem);
    }

    public static DialogUtil.Reponse confirmar(String mensagem) {
        return DialogUtil.messageConfirmation("Confirmar", mensagem);
    }

    public static DialogUtil.Reponse confirmar(String titulo, String mensagem) {
        return DialogUtil.messageConfirmation(titulo, mensagem);
    }
}
