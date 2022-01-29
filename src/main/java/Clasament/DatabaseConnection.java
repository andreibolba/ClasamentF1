package Clasament;
import Entity.PositionEntity;
import Entity.StagesEntity;
import Entity.TeamEntity;
import Entity.UsersEntity;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private Connection connection = null;
    public DatabaseConnection(){
        this.start();
    }

    public void test(String username) {
        try {
            System.out.println("1");
            String sql = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            System.out.println("2");
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("3");
            while (resultSet.next()) {
                int id = resultSet.getInt("id_user");
                String usernameParticipant = resultSet.getString("username");

                System.out.println(id + " " + usernameParticipant + '\n');
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error catch");
        }

    }

    public UsersEntity getUser(String username){
        try {
            UsersEntity usersEntity = new UsersEntity();
            String sql = "SELECT users.id_user,users.nume,users.prenume,users.username,echipa.nume as nume_echipa, monopost.marca,monopost.compond, users.roles, users.id_echipa  FROM users JOIN echipa ON echipa.id_echipa=users.id_echipa JOIN monopost ON monopost.id_monopost=users.id_monopost WHERE users.username='" + username + "'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            usersEntity.setRole("Fail");
            while (resultSet.next()) {
                usersEntity.setID(resultSet.getInt("id_user"));
                usersEntity.setUsername(resultSet.getString("username"));
                usersEntity.setfName(resultSet.getString("nume"));
                usersEntity.setlName(resultSet.getString("prenume"));
                usersEntity.setTeamName(resultSet.getString("nume_echipa"));
                usersEntity.setMonopostName(resultSet.getString("marca"));
                usersEntity.setRole(resultSet.getString("roles"));
                usersEntity.setCompond(resultSet.getString("compond"));
                usersEntity.setIdEchipa(resultSet.getInt("id_echipa"));
            }
            System.out.println();
            return usersEntity;

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error catch");
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Error finally");
            }
        }
        return new UsersEntity();
    }

    public String connectUserToApp(String username){
        try {
            if (connection != null) {
                String sql = "SELECT * FROM users WHERE username='"+username+"';";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                String role="fail";
                while (resultSet.next()) {
                    role = resultSet.getString("roles");
                    System.out.println(role);
                }
                return role;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error catch");
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Error finally");
            }
        }
        return "Fail";
    }

    public void newUser(String fName,String lName, int idTeam,int idMonopost)
    {
        String username=fName+lName;
        System.out.println(username);
            if (connection != null) {
                try {
                    String sql = "INSERT INTO users (username, nume,prenume,id_echipa,id_monopost,roles,ok) VALUES (?,?,?,?,?,?,'false')";
                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setString(1, username);
                    statement.setString(2, fName);
                    statement.setString(3, lName);
                    statement.setInt(4, idTeam);
                    statement.setInt(5, idMonopost);
                    statement.setString(6, "Concurent");
                    int rows = statement.executeUpdate();
                    if (rows > 0) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Succes register");
                        alert.setContentText("Your username is " + fName + lName);
                        alert.show();
                    }

                    String SQL="UPDATE etapa SET finished='false'";
                    PreparedStatement pstmt = connection.prepareStatement(SQL);
                    int affectedrows = pstmt.executeUpdate();
                }catch (SQLException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Taken username");
                    alert.setContentText("Username is taken! Please modify your dates! ");
                    alert.show();
                }
            }
    }

    public int count(int id_etapa){

            try {
                String sql = "SELECT COUNT(id_result) FROM results WHERE id_etapa='"+id_etapa+"';";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                int c=-1;
                while (resultSet.next()) {
                    c= resultSet.getInt("count");
                }
                return c;
            }catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Taken username");
                alert.setContentText("Username is taken! Please modify your dates! ");
                alert.show();
                return -1;
            }
    }

    public int countUser(){

        try {
            String sql = "SELECT COUNT(id_user) FROM users;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int c=-1;
            while (resultSet.next()) {
                c= resultSet.getInt("count");
            }
            return c;
        }catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Taken username");
            alert.setContentText("Username is taken! Please modify your dates! ");
            alert.show();
            return -1;
        }
    }

    public int finish(int id){
        try {
            String sqlTwo="SELECT COUNT(id_etapa) FROM etapa WHERE id_etapa='"+id+"'";
            Statement statements = connection.createStatement();
            ResultSet resultSets = statements.executeQuery(sqlTwo);
            int count=-1;
            while (resultSets.next()) {
                count= resultSets.getInt("count");
            }
            if(count>=1) {
                String sql = "SELECT finished FROM etapa WHERE id_etapa='" + id + "';";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                boolean c = false;
                while (resultSet.next()) {
                    c = resultSet.getBoolean("finished");
                }
                if(c==true)
                    return 1;
                return 0;
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid");
                alert.setContentText("Invalid stage");
                alert.show();
                return 2;
            }
        }catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
            return 3;
        }
    }

    public void newTeam(String teamName)
    {
                try {
                    String sql = "INSERT INTO echipa (nume) VALUES (?)";
                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setString(1, teamName);
                    int rows = statement.executeUpdate();
                    if (rows > 0) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Succes register");
                        alert.setContentText("Your team name is " + teamName);
                        alert.show();
                    }
                }catch (SQLException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Taken team name");
                    alert.setContentText("Team name is taken! Please modify your dates! ");
                    alert.show();
                }
    }

    public void start() {
        System.out.println("Try...");
        try{
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/formula1", "postgres", "Mateescu!97");
            if(connection != null)
                System.out.println("Connected to PostgreSQL database ...");
            else
                System.out.println("Database connection failed...");

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PositionEntity> standings(int id_etapa) throws SQLException {
        try {
            List<PositionEntity> stage=new ArrayList<PositionEntity>();
            String sql = "SELECT users.nume, users.prenume, echipa.nume AS nume_echipa, results.timp FROM results JOIN users ON users.id_user = results.id_user JOIN echipa ON echipa.id_echipa = results.id_echipa WHERE results.id_etapa ="+id_etapa+" ORDER BY results.timp;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String nume = resultSet.getString("nume");
                String prenume = resultSet.getString("prenume");
                String numeEchipa = resultSet.getString("nume_echipa");
                String timp = resultSet.getString("timp");
                PositionEntity positionEntity =new PositionEntity();
                positionEntity.setNume(nume);
                positionEntity.setPrenume(prenume);
                positionEntity.setNumeEchipa(numeEchipa);
                positionEntity.setTimp(timp);
                stage.add(positionEntity);
            }
            return stage;
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<String> getTeams(){
        try{
            List<String> teams=new ArrayList<>();
            String sql="SELECT nume FROM echipa;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                teams.add(resultSet.getString("nume"));
            }
            return teams;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAccount(int id){
        try{
            String sql="DELETE FROM users WHERE id_user=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            System.out.println("Number of records affected :: " + result);
            String SQL="UPDATE etapa SET finished='false'";
            PreparedStatement pstmt = connection.prepareStatement(SQL);
            int affectedrows = pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void deleteTeam(String team){
        try{
            String sql="DELETE FROM echipa WHERE nume=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, team);
            int result = preparedStatement.executeUpdate();
            System.out.println("Number of records affected :: " + result);
            if(result==0){
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Team delete succesfully");
                alert.setContentText("Team delete succesfully");
                alert.show();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateTeam(String oldTeam,String newTeam){
        try{;
            String sql="UPDATE echipa SET nume=? WHERE nume=?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);

                pstmt.setString(1, oldTeam);
                pstmt.setString(2, newTeam);

                int affectedrows = pstmt.executeUpdate();
            System.out.println(pstmt);
            System.out.println("Number of records affected :: " + affectedrows);
            if(affectedrows==0){
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid team name");
                alert.setContentText("Invalid team name. It was not found");
                alert.show();
            }else{
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Team name changed!");
                alert.setContentText("Team name changed!");
                alert.show();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }


    public void updateAccount(int ID,String nume,String prenume,int idEchipa,int idMonopost){
        try{
            String username=nume+prenume;
            String sql="UPDATE users SET (username,nume,prenume,id_echipa,id_monopost)=(?,?,?,?,?) WHERE id_user=?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, nume);
            pstmt.setString(3, prenume);
            pstmt.setInt(4, idEchipa);
            pstmt.setInt(5, idMonopost);
            pstmt.setInt(6, ID);

            int affectedrows = pstmt.executeUpdate();
            System.out.println("Number of records affected :: " + affectedrows);
            if(affectedrows==0){
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid team name");
                alert.setContentText("Invalid team name. It was not found");
                alert.show();
            }else{
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Team name changed!");
                alert.setContentText("Team name changed!");
                alert.show();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void addResult(int ID, int IDEtapa, int IDEchipa, LocalTime time){
        try{
            List<Integer> teams=new ArrayList<>();
            String sql="SELECT id_result FROM results WHERE id_user='"+ID+"' AND id_etapa='"+IDEtapa+"';";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                teams.add(resultSet.getInt("id_result"));
            }
            if(teams.size()!=0)
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Unavailable!");
                alert.setContentText("Result added previously!");
                alert.show();
            }else{
                String SQL = "INSERT INTO results (id_user,id_echipa,id_etapa,timp) VALUES (?,?,?,?)";
                PreparedStatement statementTwo = connection.prepareStatement(SQL);

                statementTwo.setInt(1, ID);
                statementTwo.setInt(2, IDEchipa);
                statementTwo.setInt(3, IDEtapa);
                statementTwo.setObject(4,time);
                int rows = statementTwo.executeUpdate();
                if (rows > 0) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Succes register");
                    alert.setContentText("Result added");
                    alert.show();
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateStageStatus(int ID){
        try{
            String sql="UPDATE etapa SET finished='true' WHERE id_etapa=?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, ID);

            int affectedrows = pstmt.executeUpdate();
            System.out.println("Number of records affected :: " + affectedrows);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public int isStage(int id){
        try{
            String sql="SELECT COUNT(id_etapa) FROM etapa WHERE id_etapa='"+id+"';";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            int c=0;
            while (resultSet.next()) {
                c=resultSet.getInt("count");
            }
            return c;
        }catch(SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public List<TeamEntity> getAllTeams(){
        try{
            List<TeamEntity> teams=new ArrayList<>();
            String sql="  SELECT echipa.id_echipa,echipa.nume,echipa.ok,count(users.id_echipa) AS nr_membrii FROM echipa JOIN users ON users.id_echipa = echipa.id_echipa WHERE echipa.id_echipa > -1 GROUP BY echipa.id_echipa, echipa.nume ORDER BY echipa.id_echipa;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                TeamEntity team=new TeamEntity();
                team.setId(resultSet.getInt("id_echipa"));
                team.setName(resultSet.getString("nume"));
                team.setVerify(resultSet.getBoolean("ok"));
                team.setMemebers(resultSet.getInt("nr_membrii"));
                teams.add(team);
            }
            return teams;
        }catch(SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<UsersEntity> getAllUsers(){
        try{
            List<UsersEntity> users=new ArrayList<>();
            String sql="SELECT users.id_user,users.nume,users.prenume,users.username,echipa.nume as nume_echipa, monopost.marca,monopost.compond, users.roles, users.ok FROM users JOIN echipa ON echipa.id_echipa=users.id_echipa JOIN monopost ON monopost.id_monopost=users.id_monopost WHERE id_user>-1;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                UsersEntity user=new UsersEntity();
                user.setID(resultSet.getInt("id_user"));
                user.setfName(resultSet.getString("nume"));
                user.setlName(resultSet.getString("prenume"));
                user.setUsername(resultSet.getString("username"));
                user.setTeamName(resultSet.getString("nume_echipa"));
                user.setMonopostName(resultSet.getString("marca"));
                user.setCompond(resultSet.getString("compond"));
                user.setValid(resultSet.getBoolean("ok"));
                users.add(user);
            }
            System.out.println(users.size());
            return users;
        }catch(SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void validTeam(int ID){
        try{
            String sql="UPDATE echipa SET ok='true' WHERE id_echipa=?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, ID);

            int affectedrows = pstmt.executeUpdate();
            System.out.println("Number of records affected :: " + affectedrows);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void validUser(int ID){
        try{
            String sql="UPDATE users SET ok='true' WHERE id_user=?;";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, ID);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Number of records affected :: " + affectedRows);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<StagesEntity> getAllStages(){
        try{
            List<StagesEntity> stages=new ArrayList<>();
            String sql="SELECT * FROM etapa;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                StagesEntity currentStage=new StagesEntity();
                currentStage.setId(resultSet.getInt("id_etapa"));
                currentStage.setName((resultSet.getString("nume_pista")));
                currentStage.setLocation(resultSet.getString("locatie"));
                currentStage.setDistance(resultSet.getFloat("distanta"));
                currentStage.setLaps(resultSet.getInt("ture"));
                currentStage.setDate(resultSet.getString("data_etapa"));
                currentStage.setFinished(resultSet.getBoolean("finished"));
                stages.add(currentStage);
            }
            System.out.println(stages.size());
            return stages;
        }catch(SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void insert(String nume, String loc, float distanta, int tur, LocalDate date){
        try {
            String sql = "INSERT INTO etapa (nume_pista, locatie, distanta, ture, data_etapa, finished) VALUES (?,?,?,?,?,'false')";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, nume);
            statement.setString(2, loc);
            statement.setFloat(3, distanta);
            statement.setInt(4, tur);
            statement.setDate(5, Date.valueOf(date));
            int rows = statement.executeUpdate();
            if (rows > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succes register");
                alert.setContentText("Stage added succedfully");
                alert.show();
            }
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Taken username");
            alert.setContentText("Username is taken! Please modify your dates! ");
            alert.show();
        }
    }

    public void deleteStage(int stage){
        try{
            String sql="DELETE FROM etapa WHERE id_etapa=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, stage);
            int result = preparedStatement.executeUpdate();
            System.out.println("Number of records affected :: " + result);
            if(result==0){
                Alert alert=new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Stage delete succesfully");
                alert.setContentText("Stage delete succesfully");
                alert.show();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updateStage(int id,String nume, String loc, float distanta, int tur, LocalDate date){
        try {
            String sql = "UPDATE etapa SET (nume_pista, locatie, distanta, ture, data_etapa)=(?,?,?,?,?) WHERE id_etapa=(?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, nume);
            statement.setString(2, loc);
            statement.setFloat(3, distanta);
            statement.setInt(4, tur);
            statement.setDate(5, Date.valueOf(date));
            statement.setInt(6, id);
            int rows = statement.executeUpdate();
            if (rows > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succes update");
                alert.setContentText("Stage update succedfully");
                alert.show();
            }
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Something went wrong!");
            alert.show();
        }
    }
}