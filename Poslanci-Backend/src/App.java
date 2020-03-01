import poslanciDB.entity.PoslanecEntity;
import poslanciDB.service.PoslanecEntityService;

public class App {
    public static void main(String[] args) {



        /*PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
        PoslanecEntity poslanecEntity = poslanecEntityService.find(4);
        int a = 4;*/


/*
        OrganyEntityService organyEntityService = new OrganyEntityService();
        OrganyEntity organyEntity = organyEntityService.find(4);
        System.out.println(organyEntity.getNazevOrganuCz());
*/

        /*Double sentiment = 0.0;
        Integer pocetPosSlov = 10, pocetNegSlov = 3;

        sentiment = ((pocetPosSlov * 1.0) + (pocetNegSlov * (-1.0))) / (pocetPosSlov + pocetNegSlov);
        System.out.println("Sentiment: " + sentiment);

        ProjevStatistikyEntity projevStatistikyEntity = new ProjevStatistikyEntity(null, null, null,
                null, sentiment, null);
        System.out.println("Sentiment: " + projevStatistikyEntity.getSentiment());*/


        /* projevEntityService = new ProjevEntityService();
        projevEntityService.removeAll();*/




        /*OsobyEntityService osobyEntityService = new OsobyEntityService();
        OsobyEntity osobyEntity = new OsobyEntity(2,"","ěščřžýáíé", "ĚŠČŘŽÝÁÍÉ","",
                null, "M", null, null);
        osobyEntityService.createOrUpdate(osobyEntity);*/




        /*
        StatistikyEntityService statistikyEntityService = new StatistikyEntityService();

        List list= statistikyEntityService.findAll();
        for (Object obj : list) {
            StatistikyEntity statistikyEntity;
            try {
                statistikyEntity = (StatistikyEntity) obj;
            } catch (Exception e){
                e.printStackTrace();
                continue;
            }

            OsobyEntity osobyEntity = statistikyEntity.getPoslanecByIdPoslanec().getOsobyByIdOsoba();

            System.out.println(osobyEntity.getPred() + " " + osobyEntity.getJmeno() + " "
                    + osobyEntity.getPrijmeni() + " " + osobyEntity.getZa());
            for (TopSlovaEntity tse : statistikyEntity.getTopSlovaByIdPoslanec()) {
                System.out.println(tse.getPoradi() + ": " + tse.getSlovo() + "(" + tse.getPocetVyskytu() + ")");
            }
            System.out.println();
        }*/
    }
}
