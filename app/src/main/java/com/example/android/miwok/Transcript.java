package com.example.android.miwok;

import java.util.ArrayList;

class Subject {
    private String courseCode;
    private String courseName;
    private int scu;
    private int score;

    private char gradeCode;

    public Subject(String courseCode, String courseName, int scu, int score, char gradeCode) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.scu = scu;
        this.score = score;
        this.gradeCode = gradeCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getScu() {
        return scu;
    }

    public void setScu(int scu) {
        this.scu = scu;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public char getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(char gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String toString() {
        String subjectReport = "";

        subjectReport = subjectReport + getCourseCode() + "\n" + getCourseName() + "\n" + getScu() + "\n" + getScore() + "\n" + getGradeCode() + "\n";
        return subjectReport;
    }

}

class Transcript {
    private String name, studentID;
    int semester;
    float semesterGPA;
    ArrayList<Subject> subjectsTaken = new ArrayList<Subject>();

    public Transcript(String name, String studentID, int semester, float semesterGPA, ArrayList<Subject> subjectsTaken) {
        this.name = name;
        this.studentID = studentID;
        this.semester = semester;
        this.semesterGPA = semesterGPA;
        this.subjectsTaken = subjectsTaken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public float getSemesterGPA() {
        return semesterGPA;
    }

    public void setSemesterGPA(float semesterGPA) {
        this.semesterGPA = semesterGPA;
    }

    public ArrayList<Subject> getSubjectsTaken() {
        return subjectsTaken;
    }

    public void setSubjectsTaken(ArrayList<Subject> subjectsTaken) {
        this.subjectsTaken = subjectsTaken;
    }

    public String toString() {
        String studentTranscript = "";
        studentTranscript = studentTranscript + getName() + "\n" + getStudentID() + "\n" + getSemester() + "\n" + getSemesterGPA() + "\n";

        for (Subject s : subjectsTaken) {
            studentTranscript = studentTranscript + s.toString();
        }

        return studentTranscript;
    }
}
