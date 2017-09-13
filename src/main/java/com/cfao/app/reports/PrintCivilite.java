package com.cfao.app.reports;

import com.cfao.app.beans.*;
import com.cfao.app.controllers.CivilitePhoto;
import com.cfao.app.util.ServiceproUtil;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by JP on 8/14/2017.
 */
public class PrintCivilite extends PdfReport {
    CivilitePhoto civilitePhoto = new CivilitePhoto();
    public PrintCivilite(){
        super();
    }

    public void printDetails(Personne personne) throws Exception {
        URL filename = civilitePhoto.getImagePath(personne);
        Image photo = new Image(ImageDataFactory.create(filename));
        photo.setWidth(35);
        photo.setHeight(30);
        photo.setFixedPosition(530, 803);
        this.document.add(photo);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);

        document.add(new Paragraph());
        document.add(new Paragraph("Profil Apprenant").setTextAlignment(TextAlignment.CENTER).setFont(bold).setUnderline());
        /**
         * INFORMATION GENERALE
         */
        DateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        document.add(new Paragraph("Information générale").setBold().setUnderline());
        Table infos = new Table(new float[]{5, 5, 5, 5});
        infos.setWidthPercent(100);

        infos.addCell(new Cell().add(new Paragraph("Matricule").setBold())
                .add(personne.getMatricule()).setBorder(Border.NO_BORDER));
        infos.addCell(new Cell().add(new Paragraph("Noms et Prenoms").setBold())
                .add(personne.getNom() + " " + personne.getPrenom()).setBorder(Border.NO_BORDER));


        infos.addCell(new Cell().add(new Paragraph("Date Naiss").setBold())
                .add(personne.getDatenaiss() == null ? "" : format.format(personne.getDatenaiss())).setBorder(Border.NO_BORDER));

        infos.addCell(new Cell().add(new Paragraph("Email").setBold())
                .add(personne.getEmail()).setBorder(Border.NO_BORDER));

        infos.addCell(new Cell().add(new Paragraph("Groupe").setBold())
                .add(personne.getGroupe() != null ? personne.getGroupe().getLibelle() : "").setBorder(Border.NO_BORDER));
        infos.addCell(new Cell().add(new Paragraph("Société").setBold())
                .add(personne.getSociete().getNom()).setBorder(Border.NO_BORDER));
        infos.addCell(new Cell().add(new Paragraph("Section").setBold())
                .add(personne.getSection().getLibelle()).setBorder(Border.NO_BORDER));
        infos.addCell(new Cell().add(new Paragraph("Ambition").setBold())
                .add(personne.getAmbition().getLibelle()).setBorder(Border.NO_BORDER));


        infos.addCell(new Cell().add(new Paragraph("Date Contrat").setBold())
                .add(personne.getDatecontrat() == null ? "" : format.format(personne.getDatecontrat())).setBorder(Border.NO_BORDER));
        infos.addCell(new Cell().add(new Paragraph("Pays").setBold())
                .add(personne.getPays().getNamefr()).setBorder(Border.NO_BORDER));
        if(personne.getPassport() != null) {
            infos.addCell(new Cell().add(new Paragraph("Passport").setBold())
                    .add(personne.getPassport().substring(0, personne.getPassport().lastIndexOf("."))).setBorder(Border.NO_BORDER));
        }else{
            infos.addCell(new Cell().add(new Paragraph("Passport").setBold()).setBorder(Border.NO_BORDER));
        }
        infos.addCell(new Cell().add(new Paragraph("Langue").setBold())
                .add(personne.getLangue().getLibelle()).setBorder(Border.NO_BORDER));
        document.add(infos);
        document.add(new Paragraph("Langues parlées : " + personne.getLangues()));
        if(personne.getMemo() != null) {
            document.add(new Paragraph("Memo").setBold());
            document.add(new Paragraph(personne.getMemo() + ""));
        }
        /**
         * PROFIL
         */
        Table profilTable = new Table(new float[]{1, 4, 8});
        profilTable.setWidthPercent(100f);
        profilTable.addHeaderCell(new Paragraph("N°").setBold());
        profilTable.addHeaderCell(new Paragraph("Abbréviation").setBold());
        profilTable.addHeaderCell(new Paragraph("Libellé").setBold());
        int i = 1;
        for(ProfilPersonne pp : personne.getProfilPersonnes()){
            Profil p = pp.getProfil();
            profilTable.addCell(i + "");
            profilTable.addCell(p.getAbbreviation());
            profilTable.addCell(p.getLibelle());
            i++;
        }
        document.add(new Paragraph("Profils").setBold().setUnderline());
        document.add(profilTable);
        /**
         * POSTE
         */
        Table posteTable = new Table(new float[]{1, 5, 5, 3, 3});
        posteTable.setWidthPercent(100f);
        posteTable.addHeaderCell(new Paragraph("N°").setBold());
        posteTable.addHeaderCell(new Paragraph("Titre du poste").setBold());
        posteTable.addHeaderCell(new Paragraph("Société").setBold());
        posteTable.addHeaderCell(new Paragraph("Date debut").setBold());
        posteTable.addHeaderCell(new Paragraph("Date fin").setBold());
        i = 1;
        for(Poste p : personne.getPostes()){
            posteTable.addCell(i + "");
            posteTable.addCell(p.getTitre());
            posteTable.addCell(p.getSociete().getNom());
            posteTable.addCell(p.getDatedebut() != null ? format.format(p.getDatedebut()) : "");
            posteTable.addCell(p.getDatedebut() != null ? format.format(p.getDatefin()) : "");
            i++;
        }
        document.add(new Paragraph("Postes").setBold().setUnderline());
        document.add(posteTable);
        /**
         * TABLE COMPETENCE
         */
        Table competenceTable = new Table(new float[] {1, 5, 2, 4});
        //use 100% of the width of the page
        competenceTable.setWidthPercent(100);
        competenceTable.addHeaderCell(new Paragraph("N°").setBold());
        competenceTable.addHeaderCell(new Paragraph("Intitulé de la compétence").setBold());
        competenceTable.addHeaderCell(new Paragraph("Niveau").setBold());
        competenceTable.addHeaderCell(new Paragraph("Certification").setBold());
        i = 1;
        for(PersonneCompetence pc : personne.getPersonneCompetences()){
            Competence competence = pc.getCompetence();
            competenceTable.addCell(i + "");
            competenceTable.addCell(competence.getDescription());
            competenceTable.addCell(competence.getNiveau().getLibelle());
            competenceTable.addCell(pc.getCompetenceCertification().getLibelle());
            i++;
        }
        //Font bold = FontFactory.getFont(FontConstants.HELVETICA_BOLD);
        document.add(new Paragraph("Compétences").setFont(bold).setUnderline());
        document.add(competenceTable);

        /**
         * TABLE QCM
         */
        Table qcmTable = new Table(new float[]{1, 16, 2, 2, 2});
        qcmTable.setWidthPercent(100);
        qcmTable.addHeaderCell(new Paragraph("N°").setBold());
        qcmTable.addHeaderCell(new Paragraph("Titre du Test").setBold());
        qcmTable.addHeaderCell(new Paragraph("Type du Test").setBold());
        qcmTable.addHeaderCell(new Paragraph("Note").setBold());
        qcmTable.addHeaderCell(new Paragraph("Base").setBold());
        i = 1;
        for(PersonneQcm pq : personne.getPersonneQcms()){
            Qcm qcm = pq.getQcm();
            qcmTable.addCell(i + "");
            qcmTable.addCell(qcm.getTitre());
            qcmTable.addCell(qcm.getQcmType().getLibelle());
            qcmTable.addCell(pq.getNote() + "");
            qcmTable.addCell(qcm.getBase() + "");
            i++;
        }
        document.add(new Paragraph("Tests").setFont(bold).setUnderline());
        document.add(qcmTable);
        close();
        ServiceproUtil.openDocument(destination + File.separator + "document.pdf");
    }
}
