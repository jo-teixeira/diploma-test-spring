package com.digitalhouse.obtenerdiploma.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class StudentDTO {
    @NotNull(message = "name nao pode ser null")
    @Pattern(regexp = "(^[A-Za-z\\s]{8,50})",
            message = "O nome do estudante deve ter apenas letras e tamanho entre 8 e 50.")
    private String name;
    @NotNull
    @Valid
    private List<SubjectDTO> subjects;

    public StudentDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubjectDTO> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDTO> subjects) {
        this.subjects = subjects;
    }

}
