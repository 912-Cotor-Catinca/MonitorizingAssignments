package org.example;

import org.example.domain.Nota;
import org.example.domain.Student;
import org.example.domain.Tema;
import org.example.repository.NotaXMLRepository;
import org.example.repository.StudentXMLRepository;
import org.example.repository.TemaXMLRepository;
import org.example.service.Service;
import org.example.validation.NotaValidator;
import org.example.validation.StudentValidator;
import org.example.validation.TemaValidator;
import org.example.validation.Validator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Big_Bang {
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    private Validator<Student> studentValidator = new StudentValidator();
    private Validator<Tema> temaValidator = new TemaValidator();
    private Validator<Nota> notaValidator = new NotaValidator();
    private StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
    private TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
    private NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

    private Service service = new Service(fileRepository1, fileRepository2, fileRepository3);

    @Test
    public void addGoodAssignment() {
        int result = service.saveTema("5", "ssvv", 8, 6);
        assertEquals(0, result);
    }

    @Test
    public void tc_1_AddStudent() {
        Iterable<Student> students = service.findAllStudents();
        assertEquals(6, students.spliterator().getExactSizeIfKnown());
        service.saveStudent("11", "alex", 932);
        assertEquals(6, students.spliterator().getExactSizeIfKnown());
    }
}
