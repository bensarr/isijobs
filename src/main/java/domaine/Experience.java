package domaine;


import lombok.*;

import java.time.LocalDate;
@EqualsAndHashCode()
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    private int id;
    private String libelle;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String structure;
}
