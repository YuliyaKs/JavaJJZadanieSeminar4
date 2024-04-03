package com.github.junior.homework;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Program {

    /**
     Создайте базу данных (например, SchoolDB).
     В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
     Настройте Hibernate для работы с вашей базой данных.
     Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
     Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
     Убедитесь, что каждая операция выполняется в отдельной транзакции.
     */

    public static void main(String[] args) {

        // Создание фабрики сессий
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();

        try {
            // 1 - Транзакция по созданию таблицы courses и добавлению данных в таблицу
            // Создание сессии
            Session session = sessionFactory.getCurrentSession();

            // Начало транзакции по добавлению данных
            session.beginTransaction();

            // SQL-запрос на создание таблицы
            String sql = "CREATE TABLE IF NOT EXISTS courses " +
                    "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255), " +
                    "duration INT)";

            // Создание таблицы в БД
            session.createSQLQuery(sql).addEntity(Course.class).executeUpdate();
            System.out.println("Table courses create successfully");

            // Создание объекта
            Course course = Course.create();

            session.save(course);
            System.out.println("Object course save successfully");

            // Коммит транзакции по добавлению данных
            session.getTransaction().commit();

            // 2 - Транзакция по чтению объекта из базы данных
            // Создание сессии
            session = sessionFactory.getCurrentSession();

            // Начало транзакции по чтению данных
            session.beginTransaction();

            Course retrievedCourse = session.get(Course.class, course.getId());
            System.out.println("Object course retrieved successfully");
            System.out.println("Retrieved course object: " + retrievedCourse);

            // Коммит транзакции по чтению данных
            session.getTransaction().commit();

            // 3 - Транзакция по обновлению объекта базы данных
            // Создание сессии
            session = sessionFactory.getCurrentSession();

            // Начало транзакции по обновлению данных
            session.beginTransaction();

            // Обновление объекта
            retrievedCourse.updateTitle();
            retrievedCourse.updateDuration();
            session.update(retrievedCourse);
            System.out.println("Object course update successfully");

            // Коммит транзакции по обновлению данных
            session.getTransaction().commit();

            // 4 - Транзакция по удалению объекта из базы данных
            // Создание сессии
            session = sessionFactory.getCurrentSession();

            // Начало транзакции по удалению данных
            session.beginTransaction();

            // Удаление объекта
            session.delete(retrievedCourse);
            System.out.println("Object course delete successfully");

            // Коммит транзакции по удалению данных
            session.getTransaction().commit();
        }
        finally {
            // Закрытие фабрики сессий
            sessionFactory.close();
        }

    }

}
