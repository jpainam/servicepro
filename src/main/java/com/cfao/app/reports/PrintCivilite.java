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

    public PrintCivilite() {
        super();
    }

    public void printDetails(Personne p) throws Exception {
        URL filename = civilitePhoto.getImagePath(p);
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
                .add(p.getMatricule()).setBorder(Border.NO_BORDER));
        infos.addCell(new Cell().add(new Paragraph("Noms et Prenoms").setBold())
                .add(p.getNom() + " " + p.getPrenom()).setBorder(Border.NO_BORDER));


        infos.addCell(new Cell().add(new Paragraph("Date Naiss").setBold())
                .add(p.getDatenaiss() == null ? "" : format.format(p.getDatenaiss())).setBorder(Border.NO_BORDER));

        infos.addCell(new Cell().add(new Paragraph("Email").setBold())
                .add(p.getEmail()).setBorder(Border.NO_BORDER));

        infos.addCell(new Cell().add(new Paragraph("Groupe").setBold())
                .add(p.getGroupe() != null ? p.getGroupe().getLibelle() : "").setBorder(Border.NO_BORDER));
        infos.addCell(new Cell().add(new Paragraph("Société").setBold())
                .add(p.getSociete() != null ? p.getSociete().getNom() : "").setBorder(Border.NO_BORDER));
        infos.addCell(new Cell().add(new Paragraph("Section").setBold())
                .add(p.getSection() != null ? p.getSection().getLibelle() : "").setBorder(Border.NO_BORDER));
        infos.addCell(new Cell().add(new Paragraph("Ambition").setBold())
                .add(p.getAmbition() != null ? p.getAmbition().getLibelle() : "").setBorder(Border.NO_BORDER));


        infos.addCell(new Cell().add(new Paragraph("Date Contrat").setBold())
                .add(p.getDatecontrat() == null ? "" : format.format(p.getDatecontrat())).setBorder(Border.NO_BORDER));
        infos.addCell(new Cell().add(new Paragraph("Pays").setBold())
                .add(p.getPays() != null ? p.getPays().getNamefr() : "").setBorder(Border.NO_BORDER));
        if (p.getPassport() != null) {
            Cell cell = new Cell();
            cell.add(new Paragraph("Passport").setBold());
            if (p.getPassport().lastIndexOf(".") != -1) {
                cell.add(p.getPassport().substring(0, p.getPassport().lastIndexOf("."))).setBorder(Border.NO_BORDER);
            }
            infos.addCell(cell);
        } else {
            infos.addCell(new Cell().add(new Paragraph("Passport").setBold()).setBorder(Border.NO_BORDER));
        }
        infos.addCell(new Cell().add(new Paragraph("Langue").setBold())
                .add(p.getLangue() != null ? p.getLangue().getLibelle() : "").setBorder(Border.NO_BORDER));
        document.add(infos);
        document.add(new Paragraph("Langues parlées : " + p.getLangues()));
        if (p.getMemo() != null) {
            document.add(new Paragraph("Memo").setBold());
            document.add(new Paragraph(p.getMemo() + ""));
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
        for (ProfilPersonne pp : p.getProfilPersonnes()) {
            Profil pro = pp.getProfil();
            profilTable.addCell(i + "");
            profilTable.addCell(pro.getAbbreviation());
            profilTable.addCell(pro.getLibelle());
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
        for (Poste po : p.getPostes()) {
            posteTable.addCell(i + "");
            posteTable.addCell(po.getTitre());
            posteTable.addCell(po.getSociete().getNom());
            posteTable.addCell(po.getDatedebut() != null ? format.format(po.getDatedebut()) : "");
            posteTable.addCell(po.getDatedebut() != null ? format.format(po.getDatefin()) : "");
            i++;
        }
        document.add(new Paragraph("Postes").setBold().setUnderline());
        document.add(posteTable);
        /**
         * TABLE COMPETENCE
         */
        Table competenceTable = new Table(new float[]{1, 5, 2, 4});
        //use 100% of the width of the page
        competenceTable.setWidthPercent(100);
        competenceTable.addHeaderCell(new Paragraph("N°").setBold());
        competenceTable.addHeaderCell(new Paragraph("Intitulé de la compétence").setBold());
        competenceTable.addHeaderCell(new Paragraph("Niveau").setBold());
        competenceTable.addHeaderCell(new Paragraph("Certification").setBold());
        i = 1;
        for (PersonneCompetence pc : p.getPersonneCompetences()) {
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
        for (PersonneQcm pq : p.getPersonneQcms()) {
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
