package com.jr.ordemservico.domain.util;

import java.util.Base64;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {
	/**
	 * Criptografa senha baseeada no padrão BCryptPasswordEncoder
	 * @param valor senha original
	 * @return senha criptografada
	 */
	public String criptografa(String valor){
		String senhaCriptografada = new BCryptPasswordEncoder().encode(valor);
		return senhaCriptografada;
	}

	public String criptografiaBase64Encoder(String pValor) {
	    return new String(Base64.getEncoder().encode(pValor.getBytes()));
	}
	
	public String descriptografiaBase64Decode(String pValor) {
	    return new String(Base64.getDecoder().decode(pValor.getBytes()));
	}
	
	public String descriptograBase64Reverse(String pValor) {
		String senha = descriptografiaBase64Decode(pValor);
		String senhaDecri = "";
		for (int i=0; i < senha.length(); i++) 
		{
			if ((i%2) == 0) {
			  senhaDecri = senhaDecri + senha.charAt(i);
			}
		}
		
	    return senhaDecri;
	}	 
}
