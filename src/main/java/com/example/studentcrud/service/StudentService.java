package com.example.studentcrud.service;

import com.example.studentcrud.model.Student;
import com.example.studentcrud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// CODIGO DE TREINO: contem más práticas propositadas
@Service
public class StudentService {

    @Autowired
    StudentRepository r;

    public List<Student> lista = new ArrayList<>();

    public String proc(String a, String b, String c, String d, String e) {
        try {
            Student s = new Student();
            if (a != null) {
                if (a.length() > 0) {
                    s.setRollNo(a);
                    if (b != null) {
                        if (b.length() > 0) {
                            s.setName(b);
                            if (c != null) {
                                int x = Integer.parseInt(c);
                                if (x > 0 && x < 13) {
                                    s.setSemester(x);
                                    double y = Double.parseDouble(d);
                                    if (y >= 0 && y <= 20) {
                                        s.setCgpa(y);
                                        s.setPhoneNumber(e);
                                        r.save(s);
                                        lista.add(s);
                                        System.out.println("guardado " + a);
                                        return "ok";
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return "erro";
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public double media1() {
        double t = 0;
        for (Student s : r.findAll()) {
            t = t + s.getCgpa();
        }
        return t / r.findAll().size();
    }

    public double media2() {
        double t = 0;
        for (Student s : r.findAll()) {
            t = t + s.getCgpa();
        }
        return t / r.findAll().size();
    }
}
