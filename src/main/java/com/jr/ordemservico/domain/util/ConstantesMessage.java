package com.jr.ordemservico.domain.util;

public class ConstantesMessage {
	private ConstantesMessage(){}
	
	/* Constantes gerais */
	public static final String MSG_EMAIL_EXISTENTE = "Já existe um e-mail cadastrado com o mesmo e-mail";
	
	public static final String MSG_PREENCHIMENTO_CAMPOS_INCORRETOS = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente!";
	
	/* Constantes de cliente */
	public static final String MSG_CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado";
	
	/* Constantes de Tipo de items */
	public static final String MSG_TIPO_ITEM_NAO_ENCONTRADO = "Tipo do item não encontrado";	
	
	/* Constantes de ordem serviço */
	public static final String MSG_OS_NAO_ENCONTRADO = "Ordem de serviço não encontrada";
	public static final String MSG_OS_NAO_PODE_SER_FINALIZADA = "Ordem de serviço não pode ser finalizada";
	public static final String MSG_USUARIO_JA_EXISTE = "Já existe um usuário cadastrado com esse login ou e-mail";
	
}
