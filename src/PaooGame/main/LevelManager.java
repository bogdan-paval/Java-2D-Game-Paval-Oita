package PaooGame.main;

import PaooGame.entity.Entity;
import PaooGame.entity.Player;
import PaooGame.entity.Sheep;
import PaooGame.entity.Wolf;
import PaooGame.event.GameObserver;

public class LevelManager implements GameObserver {
    private Game joc;
    private int nrMinimOiteNecesar;
    private int nrTotalOite;
    private int oiteSalvateInNivel = 0;
    private int oiteMoarteInNivel = 0;

    public LevelManager(Game joc, int minimOite, int totalOite) {
        this.joc = joc;
        this.nrMinimOiteNecesar = minimOite;
        this.nrTotalOite = totalOite;
    }

    @Override
    public void onEvent(String eventType, Entity entity) {
        if (eventType.equals("ENTITY_DIED")) {
            if (entity instanceof Player) {
                joc.setStareCurenta(Game.GameState.GAME_OVER_FADE);
            }
            else if (entity instanceof Sheep) {
                oiteMoarteInNivel++;
                verificaReguliJoc();
            }
            else if (entity instanceof Wolf) {
                joc.adaugaLupUcis();
            }
        }
        else if (eventType.equals("SHEEP_SAVED")) {
            oiteSalvateInNivel++;
            joc.adaugaOaieSalvata();
            verificaReguliJoc();
        }
    }

    private void verificaReguliJoc() {
        int oiteActivePeHarta = nrTotalOite - oiteSalvateInNivel - oiteMoarteInNivel;

        if ((nrTotalOite - oiteMoarteInNivel) < nrMinimOiteNecesar) {
            joc.setStareCurenta(Game.GameState.GAME_OVER_FADE);
        }

        if (oiteSalvateInNivel == nrTotalOite || (oiteActivePeHarta == 0 && oiteSalvateInNivel >= nrMinimOiteNecesar)) {
            joc.nivelCompletat(oiteSalvateInNivel);
        }
    }
}