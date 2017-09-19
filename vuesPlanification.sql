drop view if exists servicepro.vPlanifBeforeFormDateArrived;
drop view if exists servicepro.vPlanifBeforeFormDatePassed;
drop view if exists servicepro.vPlanifAfterFormDateArrived;
drop view if exists servicepro.vPlanifAfterFormDatePassed;
create view vPlanifAfterFormDatePassed as
select p.IDPLANIFICATION as planification, f.IDFORMATION as formation, f.DATEDEBUT as datedeb, s.LIBELLE as sujet, t.LIBELLE as tache, p.TIMING as temps, concat(r.NOM,' ',r.PRENOM) as resp
,(datediff(f.DATEDEBUT, current_date()) + p.TIMING) as tmp
from planifications p
inner join formations f on (f.IDFORMATION = p.FORMATION)
inner join sujets s on (p.SUJET = s.IDSUJET)
inner join planification_tache pt on (p.IDPLANIFICATION = pt.PLANIFICATION)
	inner join taches t on (pt.TACHE = t.IDTACHE)
inner join personnels r on (p.RESPONSABLE = r.IDPERSONNEL)
where p.FAIT = 1
and p.ALERT = 1
and p.TIMING > 0
and (datediff(current_date(), f.DATEDEBUT) - p.TIMING) >= 0
group by p.SUJET;

create view vPlanifAfterFormDateArrived as
select p.IDPLANIFICATION as planification, f.IDFORMATION as formation, f.DATEDEBUT as datedeb, s.LIBELLE as sujet, t.LIBELLE as tache, p.TIMING as temps, concat(r.NOM,' ',r.PRENOM) as resp
, abs(datediff(current_date(), f.DATEDEBUT) - p.TIMING) as tmp
from planifications p
inner join formations f on (f.IDFORMATION = p.FORMATION)
inner join sujets s on (p.SUJET = s.IDSUJET)
inner join planification_tache pt on (p.IDPLANIFICATION = pt.PLANIFICATION)
	inner join taches t on (pt.TACHE = t.IDTACHE)
inner join personnels r on (p.RESPONSABLE = r.IDPERSONNEL)
where p.FAIT = 1
and p.ALERT = 1
and p.TIMING > 0
and (datediff(current_date(), f.DATEDEBUT) - p.TIMING) <= 0
and (datediff(current_date(), f.DATEDEBUT) - p.TIMING) > -11
group by p.SUJET;

create view vPlanifBeforeFormDatePassed as
select p.IDPLANIFICATION as planification, f.IDFORMATION as formation, f.DATEDEBUT as datedeb, s.LIBELLE as sujet, t.LIBELLE as tache, p.TIMING as temps, concat(r.NOM,' ',r.PRENOM) as resp
,abs(datediff(f.DATEDEBUT, current_date()) + p.TIMING) as tmp
from planifications p
inner join formations f on (f.IDFORMATION = p.FORMATION)
inner join sujets s on (p.SUJET = s.IDSUJET)
inner join planification_tache pt on (p.IDPLANIFICATION = pt.PLANIFICATION)
	inner join taches t on (pt.TACHE = t.IDTACHE)
inner join personnels r on (p.RESPONSABLE = r.IDPERSONNEL)
where p.FAIT = 1
and p.ALERT = 1
and p.TIMING < 0
and (datediff(f.DATEDEBUT, current_date()) + p.TIMING) <= 0
and datediff(f.DATEFIN, current_date()) >= 0
group by p.SUJET;

create view vPlanifBeforeFormDateArrived as
select p.IDPLANIFICATION as planification, f.IDFORMATION as formation, f.DATEDEBUT as datedeb, s.LIBELLE as sujet, t.LIBELLE as tache, p.TIMING as temps, concat(r.NOM,' ',r.PRENOM) as resp
,(datediff(f.DATEDEBUT, current_date()) + p.TIMING) as tmp
from planifications p
inner join formations f on (f.IDFORMATION = p.FORMATION)
inner join sujets s on (p.SUJET = s.IDSUJET)
inner join planification_tache pt on (p.IDPLANIFICATION = pt.PLANIFICATION)
	inner join taches t on (pt.TACHE = t.IDTACHE)
inner join personnels r on (p.RESPONSABLE = r.IDPERSONNEL)
where p.FAIT = 1
and p.ALERT = 1
and p.TIMING < 0
and (datediff(f.DATEDEBUT, current_date()) + p.TIMING) >= 0
and (datediff(f.DATEDEBUT, current_date()) + p.TIMING) > 11
group by p.SUJET;