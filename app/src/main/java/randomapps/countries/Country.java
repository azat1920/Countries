package randomapps.countries;


public class Country {

    private String Country;
    private String Capital;
    private String[] Capitals = new String[4];
    private int id;

    Country(String country, String capital) {
        this.Country = country;
        this.Capital = capital;
    }


    Country(int i, MyDatabase db) {
        String[] list = db.ReadRowDB(i);
        Country = list[0];
        Capital = list[1];
        Capitals = db.makeCountry(Capital, i);

    }

    //Make 1 capital and 4 countries
    Country(MyDatabase db, int i) {
        String[] list = db.ReadRowDB(i);
        Country = list[0];
        Capital = list[1];
        Capitals = db.makeCapital(Country, i);

    }


    public String getCountry(){
        return Country;
    }

    public String getCapital(){
        return Capital;
    }

    public String getCapital_1(){
        return Capitals[0];
    }

    public String getCapital_2(){
        return Capitals[1];
    }

    public String getCapital_3(){
        return Capitals[2];
    }

    public String getCapital_4(){
        return Capitals[3];
    }


}
