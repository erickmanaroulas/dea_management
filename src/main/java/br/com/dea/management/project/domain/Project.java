package br.com.dea.management.project.domain;

import br.com.dea.management.employee.domain.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "project")
@NamedQuery(name = "findByName", query = "SELECT p FROM Project p where p.name = :name")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private String client;

    @ManyToOne
    @JoinColumn(name = "product_owner")
    private Employee productOwner;

    @ManyToOne
    @JoinColumn(name = "scrum_master")
    private Employee scrumMaster;

    @Column
    private String externalProductManager;
}
