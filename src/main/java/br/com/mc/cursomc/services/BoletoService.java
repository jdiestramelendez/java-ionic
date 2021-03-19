package br.com.mc.cursomc.services;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Service;

import br.com.mc.cursomc.domain.PagamentoBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoBoleto(PagamentoBoleto pagbol) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.add(Calendar.DAY_OF_YEAR, 7);
		pagbol.setDataVencimento(gc.getTime());
		
	}

}
