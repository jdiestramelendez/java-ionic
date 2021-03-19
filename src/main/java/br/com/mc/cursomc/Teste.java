package br.com.mc.cursomc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//List<String> l = List.of("1", "2", "3");
		String s = "5,2,1,4,3";
		List<Integer> lista = Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
		System.out.println(lista);
	}

}
