package com.medMais.domain.pessoa.medico;

public enum EspecialidadeMedica {

    CARDIOLOGIA("Cardiologia"),
    DERMATOLOGIA("Dermatologia"),
    PEDIATRIA("Pediatria"),
    ORTOPEDIA("Ortopedia"),
    GINECOLOGIA("Ginecologia"),
    OBSTETRICIA("Obstetrícia"),
    NEUROLOGIA("Neurologia"),
    PSIQUIATRIA("Psiquiatria"),
    OFTALMOLOGIA("Oftalmologia"),
    OTORRINOLARINGOLOGIA("Otorrinolaringologia"),
    UROLOGIA("Urologia"),
    ENDOCRINOLOGIA("Endocrinologia"),
    GASTROENTEROLOGIA("Gastroenterologia"),
    HEMATOLOGIA("Hematologia"),
    INFECTOLOGIA("Infectologia"),
    NEFROLOGIA("Nefrologia"),
    PNEUMOLOGIA("Pneumologia"),
    REUMATOLOGIA("Reumatologia"),
    ONCOLOGIA("Oncologia"),
    RADIOLOGIA("Radiologia");

    private final String descricao;

    EspecialidadeMedica(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
