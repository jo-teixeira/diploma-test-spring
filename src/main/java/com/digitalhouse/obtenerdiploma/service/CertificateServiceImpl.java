package com.digitalhouse.obtenerdiploma.service;

import com.digitalhouse.obtenerdiploma.dto.StudentDTO;
import com.digitalhouse.obtenerdiploma.dto.CertificateDTO;
import com.digitalhouse.obtenerdiploma.dto.SubjectDTO;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.Set;

@Service
public class CertificateServiceImpl implements CertificateService {
  public CertificateDTO analyzeNotes(StudentDTO notes) {
    this.springValidationRequest(notes);
    CertificateDTO response = new CertificateDTO(notes);
    response.setAverage(calculateAverage(notes));
    response.setMessage(writeDiploma(notes));
    return response;
  }

  public Double calculateAverage(StudentDTO notes) {
    int sum = notes.getSubjects().stream().mapToInt(SubjectDTO::getNote).sum();
    return sum / (double) notes.getSubjects().size();
  }

  public String writeDiploma(StudentDTO notes) {
    Double localAverage = calculateAverage(notes);
    String studentName = notes.getName();
    String message = studentName + " usted ha conseguido el promedio de " + localAverage;
    if(localAverage > 9)
      message = withHonors(localAverage, studentName);
    return message;
  }

  public String withHonors(Double localAverage, String localStudent) {
    return "¡Felicitaciones " + localStudent + "! Usted tiene el gran promedio de " + localAverage;
  }

  private void springValidationRequest(StudentDTO student) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<StudentDTO>> violations = validator.validate(student);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
