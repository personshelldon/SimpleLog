package com.don11995.log.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.don11995.log.Group;
import com.don11995.log.MapClass;
import com.don11995.log.MapField;
import com.don11995.log.SimpleLog;
import com.don11995.log.ValueMapper;

/*  Will generate new class com.don11995.log.ValueMapper
    with method "test" that mapping all variables that starts with "TEST" to their names.
    You need to add dependency
    annotationProcessor 'com.don11995.log:simple-log-processor:x.x.x'
*/
@MapClass(methods = {"test"}, prefixes = {"TEST"})
public class MainActivity
        extends Activity {

    /*  Will generate new class com.don11995.log.ValueMapper
        with method "setup" that mapping values to names.
        You need to add dependency
        annotationProcessor 'com.don11995.log:simple-log-processor:x.x.x'
     */
    @MapField("setup")
    public static final int SETUP_0 = 0;
    @MapField("setup")
    public static final int SETUP_1 = 1;
    @MapField("setup")
    public static final int SETUP_2 = 2;
    @MapField("setup")
    public static final int SETUP_3 = 3;
    @MapField("setup")
    public static final int SETUP_4 = 4;

    public static final String TEST_0 = "test0";
    public static final String TEST_1 = "test1";
    public static final String TEST_2 = "test2";
    public static final String TEST_3 = "test3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //prints "onCreate()" with tag "MainActivity"
        SimpleLog.fd();

        //prints "Test" with tag "MainActivity"
        SimpleLog.d("Test1");

        //prints "onCreate: Test" with tag "MainActivity"
        SimpleLog.fd("Test2");

        int a = SETUP_2;
        //prints "2" with tag "MainActivity"
        SimpleLog.e("Test3: %d", a);
        SimpleLog.e("Test4: " + a);

        // prints "SETUP_2" with tag "MainActivity"
        // Class ValueMapper will be auto generated from annotations
        // if annotation processor in dependencies
        SimpleLog.e("Test5: %s", ValueMapper.setup(a));

        String b = TEST_3;
        // prints "TEST_3" with tag "MainActivity"
        // Class ValueMapper will be auto generated from annotations
        // if annotation processor in dependencies
        SimpleLog.e("Test6: %s", ValueMapper.test(b));

        //prints
        //      --------Title--------
        //      String1
        //      String2
        //      ---------------------
        SimpleLog.w(new Group("Title")
                            .append("String1")
                            .append("String2"));

        NullPointerException e = new NullPointerException("Test exception");
        // print exception with full stack trace
        SimpleLog.e(e);

        // print very big message
        SimpleLog
                .i("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Tum ille: Ain tandem? Sed ad illum redeo. Cave putes quicquam esse verius. Bonum patria: miserum exilium.\n"
                           + "\n"
                           + "Quod quidem nobis non saepe contingit. Immo videri fortasse. Beatus sibi videtur esse moriens. Primum in nostrane potestate est, quid meminerimus? Quis istud, quaeso, nesciebat? Poterat autem inpune;\n"
                           + "\n"
                           + "Disserendi artem nullam habuit. Cur deinde Metrodori liberos commendas? Quis Aristidem non mortuum diligit? Quam nemo umquam voluptatem appellavit, appellat; Hoc simile tandem est?\n"
                           + "\n"
                           + "Iam contemni non poteris. In schola desinis. Quae est igitur causa istarum angustiarum? Quare conare, quaeso. Sed plane dicit quod intellegit. Summum ením bonum exposuit vacuitatem doloris;\n"
                           + "\n"
                           + "Sed ad rem redeamus; Negat esse eam, inquit, propter se expetendam. Praeteritis, inquit, gaudeo. Nihil ad rem! Ne sit sane; Sed quid sentiat, non videtis. Illa tamen simplicia, vestra versuta. Cur haec eadem Democritus? Scaevolam M.\n"
                           + "\n"
                           + "Respondeat totidem verbis. Immo videri fortasse. Videsne quam sit magna dissensio? Rationis enim perfectio est virtus; Sullae consulatum? Nihil enim hoc differt. Praeclarae mortes sunt imperatoriae; Rationis enim perfectio est virtus;\n"
                           + "\n"
                           + "Proclivi currit oratio. Quorum sine causa fieri nihil putandum est. Nos commodius agimus. Quo modo autem philosophus loquitur?\n"
                           + "\n"
                           + "Quid ergo hoc loco intellegit honestum? Venit ad extremum; Quam si explicavisset, non tam haesitaret.\n"
                           + "\n"
                           + "Velut ego nunc moveor. Certe non potest. Et nemo nimium beatus est; Negare non possum. Sed nimis multa. De hominibus dici non necesse est. Quibus ego vehementer assentior. Ostendit pedes et pectus.\n"
                           + "\n"
                           + "Si longus, levis; Et ille ridens: Video, inquit, quid agas; Quare attende, quaeso. Quare conare, quaeso. Utilitatis causa amicitia est quaesita. Suo genere perveniant ad extremum;\n"
                           + "\n"
                           + "Itaque his sapiens semper vacabit. Sed tamen intellego quid velit.\n"
                           + "\n"
                           + "Quam nemo umquam voluptatem appellavit, appellat; Erat enim Polemonis.\n"
                           + "\n"
                           + "Quonam, inquit, modo? Quam si explicavisset, non tam haesitaret. Bonum incolumis acies: misera caecitas. Idemne, quod iucunde? Ergo, inquit, tibi Q. Nos cum te, M. Eam stabilem appellas.\n"
                           + "\n"
                           + "Illi enim inter se dissentiunt. Ut pulsi recurrant?\n"
                           + "\n"
                           + "Erat enim Polemonis. Deinde dolorem quem maximum? Laboro autem non sine causa; Aliter autem vobis placet. Tum Triarius: Posthac quidem, inquit, audacius.\n"
                           + "\n"
                           + "Id mihi magnum videtur. Recte, inquit, intellegis. At enim sequor utilitatem. Qualem igitur hominem natura inchoavit? Is es profecto tu. Optime, inquam.\n"
                           + "\n"
                           + "Sed quid sentiat, non videtis. Equidem e Cn. Age sane, inquam.\n"
                           + "\n"
                           + "Satis est ad hoc responsum. Ego vero isti, inquam, permitto. Age, inquies, ista parva sunt. Hic ambiguo ludimur. Quis hoc dicit? Proclivi currit oratio. Sumenda potius quam expetenda.\n"
                           + "\n"
                           + "Maximus dolor, inquit, brevis est. Et nemo nimium beatus est; Ut pulsi recurrant?\n"
                           + "\n"
                           + "Ita prorsus, inquam; Quo tandem modo? Sint ista Graecorum; Polycratem Samium felicem appellabant.\n"
                           + "\n"
                           + "Quonam modo? Recte, inquit, intellegis. Quid iudicant sensus? Deinde dolorem quem maximum?\n"
                           + "\n"
                           + "Quae sequuntur igitur? Sit enim idem caecus, debilis. Quae duo sunt, unum facit. Ita prorsus, inquam;\n"
                           + "\n"
                           + "Deprehensus omnem poenam contemnet. Haeret in salebra. Cave putes quicquam esse verius. Eadem fortitudinis ratio reperietur. Nos vero, inquit ille; Explanetur igitur.\n"
                           + "\n"
                           + "Nulla erit controversia. Inquit, dasne adolescenti veniam? Age, inquies, ista parva sunt. Audeo dicere, inquit. Tria genera bonorum; De quibus cupio scire quid sentias. Dicimus aliquem hilare vivere;\n"
                           + "\n"
                           + "Sint ista Graecorum; Prioris generis est docilitas, memoria; Nescio quo modo praetervolavit oratio. At multis se probavit. Maximus dolor, inquit, brevis est.\n"
                           + "\n"
                           + "Recte, inquit, intellegis. Minime vero istorum quidem, inquit. Restinguet citius, si ardentem acceperit.\n"
                           + "\n"
                           + "Respondeat totidem verbis. Sed fortuna fortis; Bonum liberi: misera orbitas. Falli igitur possumus.\n"
                           + "\n"
                           + "Prodest, inquit, mihi eo esse animo. Quaerimus enim finem bonorum. Quis Aristidem non mortuum diligit? Non igitur bene. Certe, nisi voluptatem tanti aestimaretis.\n"
                           + "\n"
                           + "Sed nimis multa. Quis est tam dissimile homini. Igitur ne dolorem quidem.\n"
                           + "\n"
                           + "Idem adhuc; Hunc vos beatum; Erat enim Polemonis. Quibus ego vehementer assentior. Tria genera bonorum; Graece donan, Latine voluptatem vocant.\n"
                           + "\n"
                           + "Et quidem, inquit, vehementer errat; Venit ad extremum;\n"
                           + "\n"
                           + "Beatus sibi videtur esse moriens. Beatum, inquit. Tria genera bonorum; Qualem igitur hominem natura inchoavit? Explanetur igitur.\n"
                           + "\n"
                           + "Illa tamen simplicia, vestra versuta. Quid autem habent admirationis, cum prope accesseris? Quid iudicant sensus? Quo igitur, inquit, modo?\n"
                           + "\n"
                           + "Quo tandem modo? Reguli reiciendam; An haec ab eo non dicuntur? Tria genera bonorum; Sed nunc, quod agimus; Quid vero?\n"
                           + "\n"
                           + "Haec dicuntur inconstantissime. At enim hic etiam dolore. Sed hoc sane concedamus. Erat enim Polemonis. At coluit ipse amicitias. Itaque his sapiens semper vacabit. Idemne, quod iucunde? Minime vero istorum quidem, inquit.\n"
                           + "\n"
                           + "Audeo dicere, inquit. Hic nihil fuit, quod quaereremus. Philosophi autem in suis lectulis plerumque moriuntur.\n"
                           + "\n"
                           + "Summus dolor plures dies manere non potest? Quo modo? Sed ad illum redeo. Sint ista Graecorum; Hic nihil fuit, quod quaereremus. Ratio quidem vestra sic cogit.\n"
                           + "\n"
                           + "Proclivi currit oratio. Sed mehercule pergrata mihi oratio tua. An haec ab eo non dicuntur?\n"
                           + "\n"
                           + "Easdemne res? Invidiosum nomen est, infame, suspectum. De illis, cum volemus. Magna laus.\n"
                           + "\n"
                           + "Haec para/doca illi, nos admirabilia dicamus. Memini me adesse P. An potest cupiditas finiri? Compensabatur, inquit, cum summis doloribus laetitia. Eam stabilem appellas. Ea possunt paria non esse.\n"
                           + "\n"
                           + "Primum divisit ineleganter; Si quae forte-possumus. Est enim effectrix multarum et magnarum voluptatum. Sint modo partes vitae beatae.\n"
                           + "\n"
                           + "Sed potestne rerum maior esse dissensio? Quid de Pythagora?\n"
                           + "\n"
                           + "Eam stabilem appellas. Sequitur disserendi ratio cognitioque naturae; Sed nimis multa. Minime vero istorum quidem, inquit. Quid vero? Verum hoc idem saepe faciamus. Polycratem Samium felicem appellabant. Conferam avum tuum Drusum cum C.\n"
                           + "\n"
                           + "Sequitur disserendi ratio cognitioque naturae; Nescio quo modo praetervolavit oratio. Deprehensus omnem poenam contemnet. Non quam nostram quidem, inquit Pomponius iocans; Polycratem Samium felicem appellabant. An eiusdem modi? Qualem igitur hominem natura inchoavit?\n"
                           + "\n"
                           + "Haeret in salebra. Hic nihil fuit, quod quaereremus. Quid de Pythagora? Sed tamen intellego quid velit. Venit ad extremum; Quo tandem modo? Eaedem res maneant alio modo. Suo genere perveniant ad extremum;\n"
                           + "\n"
                           + "Nihil enim hoc differt. Quae contraria sunt his, malane?\n"
                           + "\n"
                           + "Immo alio genere; Minime vero, inquit ille, consentit. Quid enim? Hoc loco tenere se Triarius non potuit. Haeret in salebra. Sed plane dicit quod intellegit.\n"
                           + "\n"
                           + "Cur deinde Metrodori liberos commendas? Bonum liberi: misera orbitas.\n"
                           + "\n"
                           + "An potest cupiditas finiri? Verum hoc idem saepe faciamus. Quid ergo? Tum Torquatus: Prorsus, inquit, assentior; Contineo me ab exemplis.\n"
                           + "\n"
                           + "Paria sunt igitur. Nam quid possumus facere melius? Quare attende, quaeso. Mihi, inquam, qui te id ipsum rogavi? Nihil enim hoc differt. Venit ad extremum;\n"
                           + "\n"
                           + "Egone quaeris, inquit, quid sentiam? Sin aliud quid voles, postea. Quare conare, quaeso. Quid censes in Latino fore? Non semper, inquam; Confecta res esset. Si quae forte-possumus. Frater et T.\n"
                           + "\n"
                           + "Invidiosum nomen est, infame, suspectum. Vide, quantum, inquam, fallare, Torquate. Prave, nequiter, turpiter cenabat;\n"
                           + "\n"
                           + "Non igitur bene. Hic ambiguo ludimur. Sequitur disserendi ratio cognitioque naturae; Eam stabilem appellas. Quare attende, quaeso. Summum ením bonum exposuit vacuitatem doloris; Quis est tam dissimile homini. Negat esse eam, inquit, propter se expetendam.\n"
                           + "\n"
                           + "Certe non potest. Zenonis est, inquam, hoc Stoici. Utram tandem linguam nescio? Sint ista Graecorum; Quis Aristidem non mortuum diligit? Sint modo partes vitae beatae. Quare attende, quaeso.\n"
                           + "\n"
                           + "Quae est igitur causa istarum angustiarum? Pollicetur certe. Restatis igitur vos; Duo Reges: constructio interrete. Sed quae tandem ista ratio est?\n"
                           + "\n"
                           + "Ne discipulum abducam, times. Primum divisit ineleganter;\n"
                           + "\n"
                           + "Nullus est igitur cuiusquam dies natalis. Quod quidem iam fit etiam in Academia. Si longus, levis dictata sunt. Erat enim res aperta. Inquit, dasne adolescenti veniam? Quid ad utilitatem tantae pecuniae? Et quidem, inquit, vehementer errat; Quod totum contra est.\n"
                           + "\n"
                           + "Poterat autem inpune; Nescio quo modo praetervolavit oratio. Sequitur disserendi ratio cognitioque naturae; Age sane, inquam.\n"
                           + "\n"
                           + "Nam ante Aristippus, et ille melius. Nihil illinc huc pervenit. Satis est ad hoc responsum. Primum quid tu dicis breve? Venit ad extremum;\n"
                           + "\n"
                           + "Sullae consulatum? Videsne quam sit magna dissensio? Ita nemo beato beatior. Quonam, inquit, modo?\n"
                           + "\n"
                           + "Omnia peccata paria dicitis. Est, ut dicis, inquam. Suo genere perveniant ad extremum; Frater et T. Omnia peccata paria dicitis.\n"
                           + "\n"
                           + "Nihilo magis. Eadem fortitudinis ratio reperietur.\n"
                           + "\n"
                           + "Restatis igitur vos; Cur deinde Metrodori liberos commendas? Non potes, nisi retexueris illa. Graccho, eius fere, aequalí?\n"
                           + "\n"
                           + "Quod totum contra est. Tum mihi Piso: Quid ergo? Tollenda est atque extrahenda radicitus. Sit enim idem caecus, debilis.\n"
                           + "\n"
                           + "Qui est in parvis malis. Peccata paria. Confecta res esset. Sed nunc, quod agimus; Cur iustitia laudatur?\n"
                           + "\n"
                           + "Videsne quam sit magna dissensio? Ac tamen hic mallet non dolere. Bonum patria: miserum exilium. Sed quae tandem ista ratio est? A mene tu? Sed ille, ut dixi, vitiose. Moriatur, inquit. Hoc simile tandem est?\n"
                           + "\n"
                           + "Quis Aristidem non mortuum diligit? Peccata paria. Velut ego nunc moveor. Comprehensum, quod cognitum non habet?\n"
                           + "\n"
                           + "Peccata paria. Quorum sine causa fieri nihil putandum est. Eam stabilem appellas. Vestri haec verecundius, illi fortasse constantius. Nihil opus est exemplis hoc facere longius. Sed mehercule pergrata mihi oratio tua. Tu quidem reddes; Age sane, inquam.\n"
                           + "\n"
                           + "At certe gravius. Tibi hoc incredibile, quod beatissimum. Age sane, inquam. Quodsi ipsam honestatem undique pertectam atque absolutam. Nunc vides, quid faciat. Non igitur bene. Scrupulum, inquam, abeunti; Que Manilium, ab iisque M.\n"
                           + "\n"
                           + "Quae cum dixisset, finem ille. Sed ille, ut dixi, vitiose. Nos vero, inquit ille; Easdemne res? Sed haec omittamus; Frater et T.\n"
                           + "\n"
                           + "Non potes, nisi retexueris illa. Quis istud, quaeso, nesciebat? Audeo dicere, inquit. Hunc vos beatum; Haeret in salebra. Sed in rebus apertissimis nimium longi sumus. Urgent tamen et nihil remittunt.\n"
                           + "\n"
                           + "Et ille ridens: Video, inquit, quid agas; Sed mehercule pergrata mihi oratio tua. Quibusnam praeteritis?\n"
                           + "\n"
                           + "At iam decimum annum in spelunca iacet. Videsne quam sit magna dissensio? Nam ista vestra: Si gravis, brevis; Et ille ridens: Video, inquit, quid agas;\n"
                           + "\n"
                           + "Sed residamus, inquit, si placet. Sin aliud quid voles, postea. Scisse enim te quis coarguere possit? Quam si explicavisset, non tam haesitaret. Ut id aliis narrare gestiant?\n"
                           + "\n"
                           + "Eadem fortitudinis ratio reperietur. Immo videri fortasse. Quid sequatur, quid repugnet, vident. Quid sequatur, quid repugnet, vident. At certe gravius. Quam nemo umquam voluptatem appellavit, appellat;\n"
                           + "\n"
                           + "Sullae consulatum? Memini me adesse P. Tibi hoc incredibile, quod beatissimum. De illis, cum volemus. Quare conare, quaeso. Sed ne, dum huic obsequor, vobis molestus sim.\n"
                           + "\n"
                           + "Sit enim idem caecus, debilis. Quippe: habes enim a rhetoribus; Quid de Pythagora? Primum Theophrasti, Strato, physicum se voluit; Restinguet citius, si ardentem acceperit. Quae cum dixisset paulumque institisset, Quid est?\n"
                           + "\n"
                           + "Quae cum dixisset paulumque institisset, Quid est? Ratio quidem vestra sic cogit. Sed ille, ut dixi, vitiose.\n"
                           + "\n"
                           + "Tamen a proposito, inquam, aberramus. Videamus igitur sententias eorum, tum ad verba redeamus. Frater et T. Cum praesertim illa perdiscere ludus esset.\n"
                           + "\n"
                           + "ALIO MODO. Efficiens dici potest. Tibi hoc incredibile, quod beatissimum. Gloriosa ostentatio in constituendo summo bono. Numquam facies. Hunc vos beatum;\n"
                           + "\n"
                           + "Inquit, dasne adolescenti veniam? Eam tum adesse, cum dolor omnis absit; Cur iustitia laudatur? Quo igitur, inquit, modo? Efficiens dici potest. Si longus, levis. Urgent tamen et nihil remittunt.\n"
                           + "\n"
                           + "Quae duo sunt, unum facit. Videamus animi partes, quarum est conspectus illustrior; Paria sunt igitur. Pugnant Stoici cum Peripateticis.\n"
                           + "\n"
                           + "Sed ego in hoc resisto; Ostendit pedes et pectus. Egone quaeris, inquit, quid sentiam? Immo alio genere; Explanetur igitur. Ita prorsus, inquam;\n"
                           + "\n"
                           + "Ita multa dicunt, quae vix intellegam. Erat enim Polemonis. Tubulo putas dicere? Memini vero, inquam;\n"
                           + "\n"
                           + "Id mihi magnum videtur. Nihil illinc huc pervenit. Sed mehercule pergrata mihi oratio tua. Ecce aliud simile dissimile. Respondeat totidem verbis.\n"
                           + "\n"
                           + "Et nemo nimium beatus est; Est, ut dicis, inquam. Cur, nisi quod turpis oratio est? Quid vero?\n"
                           + "\n"
                           + "Sed hoc sane concedamus. Traditur, inquit, ab Epicuro ratio neglegendi doloris. Minime vero istorum quidem, inquit. Falli igitur possumus.\n"
                           + "\n"
                           + "Qui est in parvis malis. Frater et T. Primum divisit ineleganter; Graece donan, Latine voluptatem vocant. Minime vero istorum quidem, inquit. Age sane, inquam.\n"
                           + "\n"
                           + "Quae cum essent dicta, discessimus. Restatis igitur vos; Quid ad utilitatem tantae pecuniae? Sed tamen intellego quid velit. Et quidem, inquit, vehementer errat; Nullus est igitur cuiusquam dies natalis. An eiusdem modi?\n"
                           + "\n"
                           + "Sed ad illum redeo. Quid adiuvas? Si quae forte-possumus. Id est enim, de quo quaerimus. Eam stabilem appellas. Restatis igitur vos;\n"
                           + "\n"
                           + "Nam ante Aristippus, et ille melius. Tamen a proposito, inquam, aberramus. Erat enim res aperta.\n"
                           + "\n"
                           + "Nonne igitur tibi videntur, inquit, mala? Sin aliud quid voles, postea. Invidiosum nomen est, infame, suspectum. Cur iustitia laudatur?\n"
                           + "\n"
                           + "Tu quidem reddes; Nos vero, inquit ille;\n"
                           + "\n"
                           + "Tria genera bonorum; Videsne quam sit magna dissensio? Oratio me istius philosophi non offendit; Minime vero istorum quidem, inquit. Proclivi currit oratio. Qualem igitur hominem natura inchoavit? Sed in rebus apertissimis nimium longi sumus.\n"
                           + "\n"
                           + "Efficiens dici potest. Graccho, eius fere, aequalí? At enim sequor utilitatem. Suo genere perveniant ad extremum;\n"
                           + "\n"
                           + "Non igitur bene. Sed ego in hoc resisto; Sint ista Graecorum; Si longus, levis.\n"
                           + "\n"
                           + "Equidem e Cn. Dat enim intervalla et relaxat. Sumenda potius quam expetenda. Sed nimis multa. Quid ad utilitatem tantae pecuniae?\n"
                           + "\n"
                           + "Tamen a proposito, inquam, aberramus. Qui convenit? Quid de Pythagora? Ego vero isti, inquam, permitto.\n"
                           + "\n"
                           + "Nihil sane. Quae ista amicitia est? Cur iustitia laudatur? Quis istud, quaeso, nesciebat? Nam Pyrrho, Aristo, Erillus iam diu abiecti.\n"
                           + "\n"
                           + "Conferam avum tuum Drusum cum C. Quid nunc honeste dicit? Satis est ad hoc responsum. Si longus, levis. Sed plane dicit quod intellegit.");
    }
}
