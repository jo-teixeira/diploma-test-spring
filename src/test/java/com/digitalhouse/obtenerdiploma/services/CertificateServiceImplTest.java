package com.digitalhouse.obtenerdiploma.services;

import com.digitalhouse.obtenerdiploma.dto.CertificateDTO;
import com.digitalhouse.obtenerdiploma.dto.StudentDTO;
import com.digitalhouse.obtenerdiploma.dto.SubjectDTO;
import com.digitalhouse.obtenerdiploma.service.CertificateService;
import com.digitalhouse.obtenerdiploma.service.CertificateServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.ArrayList;
import java.util.List;

public class CertificateServiceImplTest {

    private final CertificateService certificateService = new CertificateServiceImpl();

    public StudentDTO setup(){
        StudentDTO studentDTO = new StudentDTO();
        SubjectDTO subjectOne = new SubjectDTO("subjectOne", 2);
        SubjectDTO subjectTwo = new SubjectDTO("subjectTwo", 4);
        SubjectDTO subjectThree = new SubjectDTO("subjectThree", 6);
        List<SubjectDTO> subjects = new ArrayList<>();

        subjects.add(subjectOne);
        subjects.add(subjectTwo);
        subjects.add(subjectThree);
        studentDTO.setName("temquepassar");
        studentDTO.setSubjects(subjects);

        return studentDTO;
    }

    @Test
    public void shouldNotReturnExceptionWhenCreateStudent(){
        StudentDTO studentDTO = this.setup();

        CertificateDTO certificateDTO = certificateService.analyzeNotes(studentDTO);

        Assertions.assertEquals("temquepassar", certificateDTO.getStudent().getName());
    }

    @Test
    public void shouldReturnExceptionWhenNameIsEmpty(){
        StudentDTO studentDTO = this.setup();

        studentDTO.setName(null);

        Assertions.assertThrows(ConstraintViolationException.class, () -> certificateService.analyzeNotes(studentDTO));
    }

    @Test
    public void shouldReturnExceptionWhenSubjectsIsEmpty(){
        StudentDTO studentDTO = this.setup();

        studentDTO.setSubjects(null);

        Assertions.assertThrows(ConstraintViolationException.class, () -> certificateService.analyzeNotes(studentDTO));
    }

    @Test
    public void shouldAnalyzeNotes(){
        StudentDTO studentDTO = setup();

        CertificateDTO certificateDTO = certificateService.analyzeNotes(studentDTO);

        Assertions.assertEquals(certificateDTO.getAverage(), 4);
        Assertions.assertEquals(certificateDTO.getMessage(), "temquepassar usted ha conseguido el promedio de 4.0");
    }

    @Test
    public void shouldNotAnalyzeNotes(){
        StudentDTO studentDTO = setup();

        CertificateDTO certificateDTO = certificateService.analyzeNotes(studentDTO);

        Assertions.assertNotEquals(certificateDTO.getAverage(), 45);
        Assertions.assertNotEquals(certificateDTO.getMessage(), "kamila usted ha conseguido el promedio de 4.0");
    }

    @Test
    public void shouldAverageSubjects(){
        StudentDTO studentDTO = setup();

        Double average = certificateService.calculateAverage(studentDTO);

        Assertions.assertEquals(average, 4);
    }

    @Test
    public void shouldNotAverageSubjects(){
        StudentDTO studentDTO = setup();

        Double average = certificateService.calculateAverage(studentDTO);

        Assertions.assertNotEquals(average, 6);
    }

    @Test
    public void shouldReturnCorrectDiploma(){
        StudentDTO studentDTO = setup();

        studentDTO.getSubjects().get(2).setNote(0);
        studentDTO.getSubjects().get(0).setNote(0);
        String diploma = certificateService.writeDiploma(studentDTO);

        Assertions.assertEquals(diploma, "temquepassar usted ha conseguido el promedio de 1.3333333333333333");
    }

    @Test
    public void shouldReturnIncorrectDiploma(){
        StudentDTO studentDTO = setup();

        studentDTO.getSubjects().get(2).setNote(0);
        studentDTO.getSubjects().get(0).setNote(0);
        String diploma = certificateService.writeDiploma(studentDTO);

        Assertions.assertNotEquals(diploma, "kamila usted ha conseguido el promedio de 6.0");
    }

    @Test
    public void shouldReturnCorrectHonor(){
        StudentDTO studentDTO = setup();

        String honor = certificateService.withHonors(4.0, studentDTO.getName());

        Assertions.assertEquals(honor, "¡Felicitaciones temquepassar! Usted tiene el gran promedio de 4.0");
    }

    @Test
    public void shouldReturnIncorrectHonor(){
        StudentDTO studentDTO = setup();

        String honor = certificateService.withHonors(4.0, studentDTO.getName());

        Assertions.assertNotEquals(honor, "kamila usted ha conseguido el promedio de 6.0");
    }
}
