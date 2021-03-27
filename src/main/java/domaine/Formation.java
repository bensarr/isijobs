package domaine;

import lombok.*;

@EqualsAndHashCode()
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Formation {
    private int id;
    private String libelle;
    private String anneDebut;
    private String anneFin;
    private String Institut;
    private Demandeur demandeur;
}
