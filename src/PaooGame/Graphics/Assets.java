package PaooGame.Graphics;

import PaooGame.map.Tile;
import PaooGame.exceptions.ResourceLoadException;
import java.awt.image.BufferedImage;

public class Assets {
    private static final int marime_cadru = 83;

    public static BufferedImage foaie_spriteuri;

    public static BufferedImage[] bogo_mers_jos = new BufferedImage[4];
    public static BufferedImage[] bogo_mers_sus = new BufferedImage[4];
    public static BufferedImage[] bogo_mers_stanga = new BufferedImage[4];
    public static BufferedImage[] bogo_mers_dreapta = new BufferedImage[4];
    public static BufferedImage[] bogo_atac = new BufferedImage[3];
    public static BufferedImage[] bogo_ranit = new BufferedImage[3];
    public static BufferedImage[] bogo_mort = new BufferedImage[3];

    public static BufferedImage[] sheep_sus = new BufferedImage[3];
    public static BufferedImage[] sheep_fundita_jos = new BufferedImage[3];
    public static BufferedImage[] sheep_fundita_dreapta = new BufferedImage[3];
    public static BufferedImage[] sheep_fundita_stanga = new BufferedImage[3];
    public static BufferedImage[] sheep_papion_jos = new BufferedImage[3];
    public static BufferedImage[] sheep_papion_dreapta = new BufferedImage[3];
    public static BufferedImage[] sheep_papion_stanga = new BufferedImage[3];
    public static BufferedImage[] sheep_cowboy_jos = new BufferedImage[3];
    public static BufferedImage[] sheep_cowboy_dreapta = new BufferedImage[3];
    public static BufferedImage[] sheep_cowboy_stanga = new BufferedImage[3];
    public static BufferedImage[] sheep_mafia_jos = new BufferedImage[3];
    public static BufferedImage[] sheep_mafia_dreapta = new BufferedImage[3];
    public static BufferedImage[] sheep_mafia_stanga = new BufferedImage[3];

    public static BufferedImage[] wolf_sus = new BufferedImage[5];
    public static BufferedImage[] wolf_jos = new BufferedImage[5];
    public static BufferedImage[] wolf_dreapta = new BufferedImage[5];
    public static BufferedImage[] wolf_stanga = new BufferedImage[5];
    public static BufferedImage[] wolf_atac_dreapta = new BufferedImage[5];
    public static BufferedImage[] wolf_atac_stanga = new BufferedImage[5];

    public static BufferedImage[] dale;

    public static BufferedImage bogo_viata;
    public static BufferedImage oaie_cap, lup_cap;
    public static BufferedImage[] bogo_viata_stari = new BufferedImage[6];

    public static void Init() throws ResourceLoadException {
        foaie_spriteuri = ImageLoader.LoadImage("/textures/spriteuri.png");

        int offsetX = 61;
        int offsetY = 27;

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                int taiereX = j * marime_cadru + offsetX;
                int taiereY = i * marime_cadru + offsetY;

                if(i == 0) bogo_mers_jos[j] = foaie_spriteuri.getSubimage(taiereX, taiereY, marime_cadru, marime_cadru);
                else if(i == 1) bogo_mers_sus[j] = foaie_spriteuri.getSubimage(taiereX, taiereY, marime_cadru, marime_cadru);
                else if(i == 2) bogo_mers_dreapta[j] = foaie_spriteuri.getSubimage(taiereX, taiereY, marime_cadru, marime_cadru);
            }
        }

        bogo_mers_stanga[0]=foaie_spriteuri.getSubimage(114, 270, 80, 80);
        bogo_mers_stanga[1]=foaie_spriteuri.getSubimage(50, 270, 80, 80);
        bogo_mers_stanga[2]=foaie_spriteuri.getSubimage(304, 273, 80, 80);
        bogo_mers_stanga[3]=foaie_spriteuri.getSubimage(226, 270, 80, 80);

        bogo_atac[0]=foaie_spriteuri.getSubimage(94, 348, 76, 74);
        bogo_atac[1]=foaie_spriteuri.getSubimage(191, 327, 47, 97);
        bogo_atac[2]=foaie_spriteuri.getSubimage(272, 352, 70, 72);

        bogo_ranit[0]=foaie_spriteuri.getSubimage(99, 424, 65, 75);
        bogo_ranit[1]=foaie_spriteuri.getSubimage(180, 425, 70, 76);
        bogo_ranit[2]=foaie_spriteuri.getSubimage(263, 423, 77, 78);

        bogo_mort[0]=foaie_spriteuri.getSubimage(86, 499, 95, 66);
        bogo_mort[1]=foaie_spriteuri.getSubimage(185, 503, 82, 63);
        bogo_mort[2]=foaie_spriteuri.getSubimage(287, 520, 99, 45);

        int startX = 979, startY = 6, latimeCadru = 51, inaltimeCadru = 50;
        for (int rand = 0; rand < 3; rand++) {
            for (int col = 0; col < 3; col++) {
                int taiereX = startX + (col * latimeCadru);
                int taiereY = startY + (rand * inaltimeCadru);
                if (rand == 0) sheep_fundita_jos[col] = foaie_spriteuri.getSubimage(taiereX, taiereY, latimeCadru, inaltimeCadru);
                else if (rand == 1) sheep_fundita_stanga[col] = foaie_spriteuri.getSubimage(taiereX, taiereY, latimeCadru, inaltimeCadru);
                else if (rand == 2) sheep_fundita_dreapta[col] = foaie_spriteuri.getSubimage(taiereX, taiereY, latimeCadru, inaltimeCadru+2);
            }
        }

        int startX_papion = 1133, startY_papion = 0, latimeCadru_papion = 52, inaltimeCadru_papion = 50;
        for (int rand = 0; rand < 4; rand++) {
            for (int col = 0; col < 3; col++) {
                int taiereX = startX_papion + (col * latimeCadru_papion);
                int taiereY = startY_papion + (rand * inaltimeCadru_papion);
                if (rand == 0) sheep_papion_jos[col] = foaie_spriteuri.getSubimage(taiereX, taiereY, latimeCadru_papion, inaltimeCadru_papion);
                else if (rand == 1) sheep_papion_stanga[col] = foaie_spriteuri.getSubimage(taiereX, taiereY, latimeCadru_papion, inaltimeCadru_papion);
                else if (rand == 2) sheep_papion_dreapta[col] = foaie_spriteuri.getSubimage(taiereX, taiereY, latimeCadru_papion, inaltimeCadru_papion);
                else if (rand == 3) sheep_sus[col] = foaie_spriteuri.getSubimage(taiereX, taiereY+2, latimeCadru_papion, inaltimeCadru_papion);
            }
        }

        int startX_cowboy = 1290, startY_cowboy = 6, latimeCadru_cowboy = 52, inaltimeCadru_cowboy = 50;
        for (int rand = 0; rand < 3; rand++) {
            for (int col = 0; col < 3; col++) {
                int taiereX = startX_cowboy + (col * latimeCadru_cowboy);
                int taiereY = startY_cowboy + (rand * inaltimeCadru_cowboy);
                if (rand == 0) sheep_cowboy_jos[col] = foaie_spriteuri.getSubimage(taiereX, taiereY, latimeCadru_cowboy, inaltimeCadru_cowboy);
                else if (rand == 1) sheep_cowboy_stanga[col] = foaie_spriteuri.getSubimage(taiereX, taiereY, latimeCadru_cowboy, inaltimeCadru_cowboy+2);
                else if (rand == 2) sheep_cowboy_dreapta[col] = foaie_spriteuri.getSubimage(taiereX, taiereY, latimeCadru_cowboy, inaltimeCadru_cowboy);
            }
        }

        int startX_mafia = 1449, startY_mafia = 1, latimeCadru_mafia = 50, inaltimeCadru_mafia = 49;
        for (int rand = 0; rand < 3; rand++) {
            for (int col = 0; col < 3; col++) {
                int taiereX = startX_mafia + (col * latimeCadru_mafia);
                int taiereY = startY_mafia + (rand * inaltimeCadru_mafia);
                if (rand == 0) sheep_mafia_jos[col] = foaie_spriteuri.getSubimage(taiereX, taiereY, latimeCadru_mafia, inaltimeCadru_mafia);
                else if (rand == 1) sheep_mafia_stanga[col] = foaie_spriteuri.getSubimage(taiereX, taiereY, latimeCadru_mafia, inaltimeCadru_mafia);
                else if (rand == 2) sheep_mafia_dreapta[col] = foaie_spriteuri.getSubimage(taiereX, taiereY, latimeCadru_mafia, inaltimeCadru_mafia);
            }
        }

        int startX_lup = 696, startY_lup = 113, latimeCadru_lup = 56, inaltimeCadru_lup = 28;
        for (int col = 0; col < 5; col++) {
            wolf_dreapta[col] = foaie_spriteuri.getSubimage(startX_lup + (col * latimeCadru_lup), startY_lup, latimeCadru_lup, inaltimeCadru_lup);
        }

        int startX_lup_stanga = 696, startY_lup_stanga = 285, latimeCadru_lup_stanga = 57, inaltimeCadru_lup_stanga = 29;
        for (int col = 0; col < 5; col++) {
            wolf_stanga[col] = foaie_spriteuri.getSubimage(startX_lup_stanga + (col * latimeCadru_lup_stanga), startY_lup_stanga, latimeCadru_lup_stanga, inaltimeCadru_lup_stanga);
        }

        int startX_lup_sus = 557, startY_lup_sus = 119, latimeCadru_lup_sus = 26, inaltimeCadru_lup_sus = 49;
        for (int col = 0; col < 4; col++) {
            wolf_sus[col] = foaie_spriteuri.getSubimage(startX_lup_sus + (col * latimeCadru_lup_sus), startY_lup_sus, latimeCadru_lup_sus, inaltimeCadru_lup_sus);
        }

        int startX_lup_jos = 415, startY_lup_jos = 113, latimeCadru_lup_jos = 26, inaltimeCadru_lup_jos = 56;
        for (int col = 0; col < 4; col++) {
            wolf_jos[col] = foaie_spriteuri.getSubimage(startX_lup_jos + (col * latimeCadru_lup_jos), startY_lup_jos, latimeCadru_lup_jos, inaltimeCadru_lup_jos);
        }

        int startX_lup_atac = 696, startY_lup_atac = 141, latimeTotala = 283, inaltime = 29, nrCadre = 5;
        int Cadru = latimeTotala / nrCadre;
        wolf_atac_dreapta = new BufferedImage[nrCadre];
        for (int i = 0; i < nrCadre; i++) {
            wolf_atac_dreapta[i] = foaie_spriteuri.getSubimage(startX_lup_atac + (i * Cadru), startY_lup_atac, Cadru, inaltime);
        }

        int startX_lup_atac2 = 696, startY_lup_atac2 = 314;
        latimeTotala = 285; inaltime = 31; nrCadre = 5;
        int latimeCadruAtac = latimeTotala / nrCadre;
        wolf_atac_stanga = new BufferedImage[nrCadre];
        for (int i = 0; i < nrCadre; i++) {
            wolf_atac_stanga[i] = foaie_spriteuri.getSubimage(startX_lup_atac2 + (i * latimeCadruAtac), startY_lup_atac2, latimeCadruAtac, inaltime);
        }

        BufferedImage imagineMare = ImageLoader.LoadImage("/textures/pozafinala.jpeg");
        int dalePeLatime = 64, dalePeInaltime = 26;
        dale = new BufferedImage[dalePeLatime * dalePeInaltime];
        int index = 0;
        for (int y = 0; y < dalePeInaltime; y++) {
            for (int x = 0; x < dalePeLatime; x++) {
                dale[index] = imagineMare.getSubimage(x * 16, y * 16, 16, 16);
                index++;
            }
        }
        for(int i = 0; i < dale.length; i++) {
            if(dale[i] != null) new Tile(dale[i], i);
        }

        BufferedImage imagineNivel2 = ImageLoader.LoadImage("/textures/nivel2sprite.png");
        int dalePeLatime2 = 32, dalePeInaltime2 = 32;
        BufferedImage[] daleNivel2 = new BufferedImage[dalePeLatime2 * dalePeInaltime2];
        int index2 = 0;
        for (int y = 0; y < dalePeInaltime2; y++) {
            for (int x = 0; x < dalePeLatime2; x++) {
                daleNivel2[index2] = imagineNivel2.getSubimage(x * 16, y * 16, 16, 16);
                index2++;
            }
        }
        int offsetNivel2 = 2000;
        for(int i = 0; i < daleNivel2.length; i++) {
            if(daleNivel2[i] != null) new Tile(daleNivel2[i], i + offsetNivel2);
        }

        BufferedImage imagineNivel3 = ImageLoader.LoadImage("/textures/nivel3_sprite.png");
        int dalePeLatime3 = 32, dalePeInaltime3 = 32;
        BufferedImage[] daleNivel3 = new BufferedImage[dalePeLatime3 * dalePeInaltime3];
        int index3 = 0;
        for (int y = 0; y < dalePeInaltime3; y++) {
            for (int x = 0; x < dalePeLatime3; x++) {
                daleNivel3[index3] = imagineNivel3.getSubimage(x * 16, y * 16, 16, 16);
                index3++;
            }
        }
        int offsetNivel3 = 3000;
        for(int i = 0; i < daleNivel3.length; i++) {
            if(daleNivel3[i] != null) new Tile(daleNivel3[i], i + offsetNivel3);
        }

        BufferedImage viataSheet = ImageLoader.LoadImage("/textures/bogo_hp.jpeg");

        int cadruW = viataSheet.getWidth() / 2;
        int cadruH = viataSheet.getHeight() / 3;

        bogo_viata_stari[5] = viataSheet.getSubimage(0, 0, cadruW, cadruH);
        bogo_viata_stari[2] = viataSheet.getSubimage(cadruW, 0, cadruW, cadruH);
        bogo_viata_stari[4] = viataSheet.getSubimage(0, cadruH, cadruW, cadruH);
        bogo_viata_stari[1] = viataSheet.getSubimage(cadruW, cadruH, cadruW, cadruH);
        bogo_viata_stari[3] = viataSheet.getSubimage(0, cadruH * 2, cadruW, cadruH);
        bogo_viata_stari[0] = viataSheet.getSubimage(cadruW, cadruH * 2, cadruW, cadruH);

        oaie_cap = ImageLoader.LoadImage("/textures/oaie_cap.png");
        lup_cap = ImageLoader.LoadImage("/textures/lup_cap.png");
    }
}