package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DateUtil.NOW;
import static ru.javawebinar.basejava.util.DateUtil.of;

public class Organization  {

    private Link homePage;
    private List<Organization.Position> positions;

    public Organization(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return homePage.equals(that.homePage) &&
                positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", positions=" + positions +
                '}';
    }

     public static class Position {

        private LocalDate dateStart;
        private LocalDate dateEnd;
        private String title;
        private String description;

         public Position() {
         }

         public Position(int startYear, Month startMonth, String title, String description) {
             this(of(startYear, startMonth), NOW, title, description);
         }

         public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
             this(of(startYear, startMonth), of(endYear, endMonth), title, description);
         }

        public Position(LocalDate dateStart, LocalDate dateEnd, String title, String description) {
            Objects.requireNonNull(dateStart, "OOO! dateStart");
            Objects.requireNonNull(dateEnd, "OOO! dateEnd");
            Objects.requireNonNull(title, "OOO! title");
//            Objects.requireNonNull(description, "OOO! description");
            this.dateStart = dateStart;
            this.dateEnd = dateEnd;
            this.title = title;
            this.description = description;
        }

        public LocalDate getDateStart() {
            return dateStart;
        }

        public LocalDate getDateEnd() {
            return dateEnd;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;

            return Objects.equals(dateStart, position.dateStart) &&
                    Objects.equals(dateEnd, position.dateEnd) &&
                    Objects.equals(title, position.title) &&
                    Objects.equals(description, position.description);

        }



        @Override
        public int hashCode() {
            return Objects.hash(dateStart, dateEnd, title, description);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "dateStart=" + dateStart +
                    ", dateEnd=" + dateEnd +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
