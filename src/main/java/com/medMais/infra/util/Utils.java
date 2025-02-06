package com.medMais.infra.util;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.medMais.domain.pessoa.medico.EspecialidadeMedica;

@Component
public class Utils {
	
	private static final Set<LocalDate> FERIADOS = new HashSet<>();

	static {
	    //FERIADOS
	    FERIADOS.add(LocalDate.of(2025, 1, 1));   // ANO NOVO
	    FERIADOS.add(LocalDate.of(2025, 3, 4));   // CARNAVAL
	    FERIADOS.add(LocalDate.of(2025, 4, 18));  // SEXA SANTA
	    FERIADOS.add(LocalDate.of(2025, 4, 21));  // TIRADENTES
	    FERIADOS.add(LocalDate.of(2025, 5, 1));   // DIA DO TRABALHADOR
	    FERIADOS.add(LocalDate.of(2025, 6, 19));  // CORPUS CHRISTI
	    FERIADOS.add(LocalDate.of(2025, 9, 7));   // INDEP BRASIL
	    FERIADOS.add(LocalDate.of(2025, 10, 12)); // SENHORA APARECIDA
	    FERIADOS.add(LocalDate.of(2025, 11, 2));  // FINADOS
	    FERIADOS.add(LocalDate.of(2025, 11, 15)); // PROC REPUBLICA
	    FERIADOS.add(LocalDate.of(2025, 12, 25)); // NATAL
	}
	
	//IFs Para testar se nao e nulo ou branco de cada VARIAVEL
	
    public boolean isNullOrEmptyString(String str) {
        return str == null || str.isBlank();
    }

    public boolean isNullOrEmptyCategoria(EspecialidadeMedica esp) {
        return esp == null;
    }

    public boolean isNullOrEmptyFloat(BigDecimal  f) {
        return f == null;
    }
    
    public boolean isNullOrEmptyDate(LocalDate d) {
        return d == null;
    }
    
    // DIA UIL E FERIADOS
    
    public static boolean eDiaUtil(LocalDateTime data) {
        DayOfWeek diaDaSemana = data.getDayOfWeek();
        return diaDaSemana != DayOfWeek.SATURDAY && diaDaSemana != DayOfWeek.SUNDAY;
    }
    
    public static boolean eFeriado(LocalDate data) {
        return FERIADOS.contains(data);
    }
    
    //Usar soemnte esse 
    public static boolean eDataValida(LocalDateTime data) {
        return eDiaUtil(data) && !eFeriado(data.toLocalDate());
    }

}
