package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Pattern;
import java.time.temporal.ChronoUnit;
import java.io.*;

import static cycling.StageType.TT;


/**
 * BadCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the CyclingPortal interface.
 * 
 * @author Diogo Pacheco
 * @version 2.0
 *
 */
public class CyclingPortalImpl implements CyclingPortal {


	@Override
	public int[] getRaceIds() {
		// This method initialises an array and iterates through
		// the list of races in the Race class, gets the race ids and
		// stores them to a list of race ids and returns them.
		int[] race_id_array = new int[0];
		int i = 0;
		while (Race.races[i] != null) {
			int RaceID = Race.races[i].race_id;
			race_id_array[i] = RaceID;
			i+=1;
		}
		return race_id_array;
	}


	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		// This method is used to create a race using the name and description given.
		// The method checks if the name is empty or null and also checks if the name contains only normal characters.
		// A valid exception is thrown if needed and the id of the race is returned.
		int id;
		if (name == null || name.isEmpty()) {
			throw new InvalidNameException("Race name cannot be null or empty");
		}

		if (!name.matches("[a-zA-Z ]+")) {
			throw new IllegalNameException("Race name can only contain letter");
		}
		 Race race = new Race(name,description);
		id = race.getRace_id();
		return id;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		// Using getters, the details of a race can be returned using a races unique id.
		Race race = Race.getRace(raceId);
		String name = race.race_name;
		String description = race.description;
		String details = name + ' ' + description;

		return details;
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		// This method gets the race and removes from the list of races by replacing the race object with null
		Race race = Race.getRace(raceId);
		Race[] race_list = Race.races;
		boolean found = false;
		int i = 0;
		while(!found){
			if (Race.races[i] == race){
				found = true;
				Race.races[i] = null;
			} else {
				i += 1;
			}
		}
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		// Using the race id and the getRace method,
		// the number of stages that the races has can be returned.
		int stage_count;
		Race curr = Race.getRace(raceId);
		stage_count = (curr.getStages()).size();
		return stage_count;
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {

		// This method checks the incoming parameters to see if they comply with the standards given,
		// and throws an exception accordingly.
		// The method then creates a new stage object,
		// and adds to the array-list of stages in the race object given and returns the stage id made.

		if (length < 5){
			throw new InvalidLengthException();
		}

		String regex = "^[a-zA-Z]+$";
		Pattern pattern = Pattern.compile(regex);

		if (!(pattern.matcher(stageName).matches()) || stageName.contains(" ") || stageName == null || stageName.isEmpty()){
			throw new InvalidNameException();
		}

		boolean found = false;
		int i = 0;
		Race race = Race.getRace(raceId);
		int stage_count = race.getStages().size();
		while (!found){
			Stage stage = race.stages.get(i);
			if (stage.stageName == stageName){
				found = true;

			} else {
				i+=1;
			}
		}
		if ((i == stage_count) && found){
			throw new IllegalNameException();
		}


		Stage stage;
		stage = new Stage(type, stageName, description, length, startTime); // creating a new stage object
		int stageId = stage.getStageId();
		stage.active_status = true;
		race.stages.add(stage);
		return stageId;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		// This method returns a list of stage ids from the specified race id
		// by using a for loop and assigning each stage id to a temporary array-list
		Race curr = Race.getRace(raceId);
		int num_stages = getNumberOfStages(raceId);
		int[] stageIds = new int[num_stages];

		ArrayList<Stage>stage_list = new ArrayList<>();
		stage_list = curr.getStages();

		for (int j = 0; j <= num_stages; j++) {
			Stage temp = stage_list.get(j);
			int temp_id = temp.getStageId();
			stageIds[j] = temp_id;
		}
		return stageIds;
	}
	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		// A getter is used to return a value for the length of the stage using the stage id required
		double stageLength;
		Stage curr = Stage.getStage(stageId);
		stageLength = curr.getStage_length();
		return stageLength;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		Stage removestage = Stage.getStage(stageId); // a getter to get stage id
		if (removestage != null) {
			for (int i = 0; i < Stage.stages.length; i++) { // for loop to iterate through list of stages
				if (Stage.stages[i] == removestage) {
					Stage.stages[i] = null; // replaces the specified stage with null
					break;
				}
			}
		} else {
			throw new IDNotRecognisedException(); // throw exception if the stage is null
		}
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {

		int checkpoint_id = 0;
		Stage stage = Stage.getStage(stageId);// get stage object
		double stage_length = getStageLength(stageId);

		if (location < 0.0 || location > stage_length){
			throw new InvalidLocationException(); // check if location is in range and throw exception
		}

		if (stage.getStage_type() == TT){
			throw new InvalidStageTypeException(); // check if the stage is a time trial as the checkpoint would be invalid
		}

		if (stage.getActiveStatus() == false){ // make sure the stage is able to edited
			throw new InvalidStageStateException();
		}

		Checkpoint c_climb = new Checkpoint(location, type, averageGradient, length); // create new checkpoint object
		stage.checkpoints.add(c_climb); // add object to list of checkpoints of that stage
		checkpoint_id = c_climb.getCheckpoint_id(); // get checkpoint id and return it
		return checkpoint_id;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {

		int checkpoint_id = 0;
		Stage stage = Stage.getStage(stageId);
		double stage_length = getStageLength(stageId);

		if (location < 0.0 || location > stage_length){
			throw new InvalidLocationException(); // validate the location of the checkpoint
		}

		if (stage.getStage_type() == TT){// make sure the stage is not a time trial
			throw new InvalidStageTypeException();
		}

		if (stage.getActiveStatus() == false){  // make sure the stage can be edited
			throw new InvalidStageStateException();
		}

		Checkpoint i_sprint = new Checkpoint(location);// new checkpoint object creation
		stage.checkpoints.add(i_sprint);
		checkpoint_id = i_sprint.getCheckpoint_id(); // get checkpoint id and return it

		return checkpoint_id;
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		Checkpoint checkpoint = Checkpoint.getCheckpoint(checkpointId);
		if (checkpoint != null) {
			for (int i = 0; i < Checkpoint.checkpoints.length; i++) { // for loop to iterate through list of stages
				if (Checkpoint.checkpoints[i] == checkpoint) {
					Checkpoint.checkpoints[i] = null; // replaces the specified stage with null
					break;
				}
			}
		} else {
			throw new IDNotRecognisedException(); // throw exception if the stage is null
		}
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		Stage stage = Stage.getStage(stageId);
		if (stage.active_status == false){ // checks if the stage is active and changes the stage to its finish state if it's done being edited
			stage.finish_state = true;
		} else {
			stage.finish_state = false;
			throw new InvalidStageStateException();
		}
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		int count = ((Stage.stages[stageId]).checkpoints).size();
		int[] stage_checkpoints = new int[count]; // create a list for stage checkpoints

		for (int j = 0; j<count; j++){
			Stage stage = Stage.getStage(stageId); // get stage object
			Checkpoint element = stage.checkpoints.get(j); // use stage object and get the objects checkpoints
			stage_checkpoints[j] = element.getCheckpoint_id();
		}
		return stage_checkpoints;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {

		//for (int i = 0; i < Stage.teams.size(); i++){
			//Teams team = Stage.teams.get(i);
			//if (Objects.equals(team.getName(), name)){
				//throw new IllegalNameException();
			//}
		//}

		String regex = "^[a-zA-Z]+$";
		Pattern pattern = Pattern.compile(regex);

		if (!(pattern.matcher(name).matches()) || name.contains(" ") || name == null || name.isEmpty()){
			throw new InvalidNameException();
		}

		Teams newTeam = new Teams(name,description);
		int newteam_id = newTeam.getTeamId();
		Stage.teams.add(newTeam);
        return newteam_id;
    }

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		Teams team = Teams.getTeam(teamId);
		if (team != null) {
			for (int i = 0; i < Stage.teams.size(); i++) { // for loop to iterate through list of teams
				if (Stage.teams.get(i) == team) {
					Stage.teams.set(i, null); // replaces the specified team with null
					break;
				}
			}
		} else {
			throw new IDNotRecognisedException(); // throw exception if the team is null
		}
	}

	@Override
	public int[] getTeams() {
		int counter = Stage.teams.size();
		int[] team_ids = new int[counter];
		for (int i =0; i<counter;i++){
			Teams curr = Teams.teams[i];
			team_ids[i] = curr.team_id;
		}
		return team_ids;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		Teams team = Teams.getTeam(teamId);
		int riders = team.getTeam_riders().size();
		int[] team_riders = new int[riders];
		for (int j =0; j<riders; j++){
			team_riders[j] = team.getTeam_riders().get(j).getId();
		}

		return team_riders;
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		Teams team = Teams.getTeam(teamID);
		Riders rider = new Riders(teamID,name,yearOfBirth);
		team.team_riders.add(rider);
		int id = rider.getId();

		return id;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		Riders rider = Riders.getRider(riderId);
		if (rider != null) {
			for (int i = 0; i < Riders.riders.length; i++) { // for loop to iterate through list of riders
				if (Riders.riders[i] == rider) {
					Riders.riders[i] = null; // replaces the specified rider with null
					break;
				}
			}
		} else {
			throw new IDNotRecognisedException(); // throw exception if the rider is null
		}
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		LocalTime start;
		LocalTime finish;
		boolean found = false;
		Riders current_rider = Riders.riders[0];
		Stage current_stage = Stage.stages[0];
		int i = 0;
		int rider_counter =0;
		while (!found) {
			current_rider = Riders.riders[i];
			if (current_rider.id == riderId) {
				found = true;
				rider_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		found = false;
		i = 0;
		int stage_counter = 0;
		while (!found) {
			current_stage = Stage.stages[i];
			if (current_stage.stageId == stageId) {
				found = true;
				stage_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		LocalTime duration;
		int checkpoint_count = current_stage.getCheckpoints().size();
		LocalTime[] times = new LocalTime[checkpoint_count];
		for (int j =0; j<checkpoint_count; j++){
			Checkpoint curr = Checkpoint.checkpoints[j];
			start = curr.riders[rider_counter].start_time;
			finish = curr.riders[rider_counter].finish_time;
			long durationInSeconds = ChronoUnit.SECONDS.between(start, finish);
			duration = LocalTime.ofSecondOfDay(durationInSeconds);
			times[j] = duration;
		}
		LocalTime totalelapsedtime = Arrays.stream(times).reduce(LocalTime.MIDNIGHT, (t1, t2) -> t1.plusHours(t2.getHour()).plusMinutes(t2.getMinute()).plusSeconds(t2.getSecond()));
		Stage.stages[stage_counter].riders[rider_counter].totalElapsedTime = totalelapsedtime;
		Results.elapsedTimes = times;
		return times;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		boolean found = false;
		Riders current_rider = Riders.riders[0];
		Stage current_stage = Stage.stages[0];
		int i = 0;
		int rider_counter =0;
		while (!found) {
			current_rider = Riders.riders[i];
			if (current_rider.id == riderId) {
				found = true;
				rider_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		found = false;
		i = 0;
		int stage_counter = 0;
		while (!found) {
			current_stage = Stage.stages[i];
			if (current_stage.stageId == stageId) {
				found = true;
				stage_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		LocalTime result = LocalTime.ofSecondOfDay(0);


		Riders current = Stage.stages[stage_counter].riders[rider_counter];
		LocalTime current_start_time = current.start_time;
		LocalTime current_finish_time = current.finish_time;
		long durationInSeconds_current = ChronoUnit.SECONDS.between(current_start_time, current_finish_time);
		LocalTime current_duration = LocalTime.ofSecondOfDay(durationInSeconds_current);


		Riders previous = Stage.stages[stage_counter].riders[rider_counter-1];
		Riders next = Stage.stages[stage_counter].riders[rider_counter+1];
		LocalTime previous_start_time = previous.start_time;
		LocalTime previous_finish_time = previous.finish_time;
		LocalTime next_start_time = next.start_time;
		LocalTime next_finish_time = next.finish_time;
		long durationInSeconds_previous = ChronoUnit.SECONDS.between(previous_start_time, previous_finish_time);
		LocalTime previous_duration = LocalTime.ofSecondOfDay(durationInSeconds_previous);
		long durationInSeconds_next = ChronoUnit.SECONDS.between(next_start_time, next_finish_time);
		LocalTime next_duration = LocalTime.ofSecondOfDay(durationInSeconds_next);

		if (Stage.stages[stage_counter].stage_type == TT){
			result = current_duration;
		}

		if (previous_duration.plusSeconds(1).getSecond() > current_duration.getSecond()){
			result = previous_duration;
		}

		if (next_duration.plusSeconds(1).getSecond() > current_duration.getSecond()){
			result = next_duration;
		}

		if (previous_duration.plusSeconds(1).getSecond() < current_duration.getSecond() ||next_duration.plusSeconds(1).getSecond() < current_duration.getSecond() ){
			result = current_duration;
		}

		return result;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		boolean found = false;
		Riders current_rider = Riders.riders[0];
		Stage current_stage = Stage.stages[0];
		int i = 0;
		int rider_counter=0;
		while (!found) {
			current_rider = Riders.riders[i];
			if (current_rider.id == riderId) {
				found = true;
				rider_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		found = false;
		i = 0;
		int stage_counter = 0;
		while (!found) {
			current_stage = Stage.stages[i];
			if (current_stage.stageId == stageId) {
				found = true;
				stage_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		Stage temp = Stage.stages[stage_counter];
		Riders current = temp.riders[rider_counter];
		current.results = null;
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		LocalTime[] elapsedTimes = Results.elapsedTimes;
		boolean found = false;
		int i = 0;
		int stage_counter = 0;
		Stage current_stage = Stage.stages[0];
		while (!found) {
			current_stage = Stage.stages[i];
			if (current_stage.stageId == stageId) {
				found = true;
				stage_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		int counter = Stage.stages[stage_counter].riders.length;
		int[] riderIDs = new int[counter];

		for (int k=0; k<counter;k++){
			Riders temp = Stage.stages[stage_counter].riders[k];
			riderIDs[k] = temp.id;
		}

		LocalTime[] times = new LocalTime[counter];


		for (int j=0; j<counter; j++){
			int id = riderIDs[j];
			times[j] = Results.sumLocalTimes(getRiderResultsInStage(stageId,id));
		}

		int[] totalSecondsArray = Results.convertToTotalSecondsArray(times);
		int size = totalSecondsArray.length;
		for (int a = 0; a<size-1; a++){
			for (int b = 0; b<size-a-1; b++){
				if(totalSecondsArray[b+1]>totalSecondsArray[b]){
					int temp1 = totalSecondsArray[b];
					totalSecondsArray[b] = totalSecondsArray[b+1];
					totalSecondsArray[b+1] = temp1;
					int temp2 = riderIDs[b];
					riderIDs[b] = riderIDs[b+1];
					riderIDs[b+1] = temp1;
				}
			}

		}

		int[] riders= new int[15];
		int max = 0;
		while (max<15){
			riders[max] = riderIDs[max];
			max++;
		}

		return riders;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		int stage_counter = 0;
		Stage current_stage = Stage.stages[0];
		while (!found) {
			current_stage = Stage.stages[i];
			if (current_stage.stageId == stageId) {
				found = true;
				stage_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		int counter = Stage.stages[stage_counter].riders.length;
		LocalTime[] rankedTimes = new LocalTime[15];
		LocalTime[] adjustedRankedTimes = new LocalTime[15];

		for (int j = 0; j<15;j++){
			int riderID = Stage.stages[stage_counter].riders[j].id;
			rankedTimes = getRiderResultsInStage(stageId,riderID);
			for (int k = 0; k< 15; k++){
				rankedTimes[k] = getRiderAdjustedElapsedTimeInStage(stageId,riderID);
			}
			LocalTime totaladjustedelapsedtime = Arrays.stream(rankedTimes).reduce(LocalTime.MIDNIGHT, (t1, t2) -> t1.plusHours(t2.getHour()).plusMinutes(t2.getMinute()).plusSeconds(t2.getSecond()));
			adjustedRankedTimes[j] = totaladjustedelapsedtime;
		}


		return adjustedRankedTimes;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		int stage_counter = 0;
		Stage current_stage = Stage.stages[0];
		while (!found) {
			current_stage = Stage.stages[i];
			if (current_stage.stageId == stageId) {
				found = true;
				stage_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		int counter = Stage.stages[stage_counter].riders.length;
		int[] rankedRiders = new int[counter];
		rankedRiders = getRidersRankInStage(stageId);
		int[] riders= new int[15];
		int max = 0;
		while (max<15){
			riders[max] = rankedRiders[max];
			max++;
		}
		int[] riderPoints = new int[15];
		riderPoints = Stage.stagePoints(stage_counter);

		int checkPoints = Stage.stages[stage_counter].checkpoints.size();
		int sprintCount = 0;
		for (int j=0; j<checkPoints;i++){
			if (Stage.stages[stage_counter].checkpoints.get(j).checkpoint_type == CheckpointType.SPRINT){
				sprintCount = sprintCount+1;
			}
		}
		int[] sprintPoints = new int[15];
		sprintPoints = Checkpoint.checkpointSprintPoints();
		for (int a = 0; a<sprintCount; a++){
			for (int b = 0; b<15; b++){
				riderPoints[b] = riderPoints[b]+sprintPoints[b];
			}

		}
		return riderPoints;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		int stage_counter = 0;
		Stage current_stage = Stage.stages[0];
		int[] points = new int[15];
		int m = 0;
		while (m<15){
			points[m] = 0;
			m++;
		}
		while (!found) {
			current_stage = Stage.stages[i];
			if (current_stage.stageId == stageId) {
				found = true;
				stage_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		int[] ridersMountainPoints = new int[15];
		int checkPoints = Stage.stages[stage_counter].checkpoints.size();
		for (int j = 0; j < checkPoints; i++) {
				CheckpointType type = Stage.stages[stage_counter].checkpoints.get(j).checkpoint_type;
				switch (type) {
					case C4:
						ridersMountainPoints = Checkpoint.C4Points();
					case C3:
						ridersMountainPoints = Checkpoint.C3Points();
					case C2:
						ridersMountainPoints = Checkpoint.C2Points();
					case C1:
						ridersMountainPoints = Checkpoint.C1Points();
					case HC:
						ridersMountainPoints = Checkpoint.HCPoints();
					case SPRINT:
						ridersMountainPoints = null;
				for (int a = 0; a < ridersMountainPoints.length; a++ ) {
					points[a] = points[a]+ridersMountainPoints[a];
				}
			}
	}
		return points;
	}

	@Override
	public void eraseCyclingPortal() {
		CyclingPortal cp = new CyclingPortalImpl();
		cp.equals(null);
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		try{
			File cycling_portal = new File(filename);
			if (cycling_portal.createNewFile()){
				BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
				writer.close();
			} else {
				BufferedWriter filewriter = new BufferedWriter(new FileWriter(filename,false));
				filewriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while(line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		Race raceToRemove = Race.getRacebyName(name);
		int current_size = raceToRemove.stages.size();
		int current_riders = raceToRemove.riders.length;
		for (int j = 0; j< current_riders; j++){
			raceToRemove.riders[j].results = null;

		}
		for (int i = 0; i< current_size; i++){
			raceToRemove.stages.get(i).riders = null;
			raceToRemove.stages.get(i).checkpoints.clear();
		}
		raceToRemove.stages.clear();
	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		int race_counter = 0;
		Race current_race = Race.races[0];
		while (!found) {
			current_race = Race.races[i];
			if (current_race.race_id == raceId) {
				found = true;
				race_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}

		int StageCount = Race.races[race_counter].stages.size();
		LocalTime[] adjustedTimes = new LocalTime[StageCount];
		LocalTime[] totaladjustedTimes = new LocalTime[15];

		for (int j = 0; j<StageCount; j++){
			int StageID = Race.races[race_counter].stages.get(j).stageId;
			adjustedTimes = getRankedAdjustedElapsedTimesInStage(StageID);
			int riderCount = Race.races[race_counter].stages.get(j).riders.length;
			for (int k = 0; k<riderCount; k++){
				LocalTime time1 = adjustedTimes[k];
				LocalTime time2 = totaladjustedTimes[k];
				LocalTime sum = time1.plusHours(time2.getHour())
						.plusMinutes(time2.getMinute())
						.plusSeconds(time2.getSecond())
						.plusNanos(time2.getNano());
				totaladjustedTimes[k] = sum;
			}
		}
		Arrays.sort(totaladjustedTimes, Comparator.naturalOrder());



		return totaladjustedTimes;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		int race_counter = 0;
		Race current_race = Race.races[0];
		while (!found) {
			current_race = Race.races[i];
			if (current_race.race_id == raceId) {
				found = true;
				race_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		int StageCount = Race.races[race_counter].stages.size();

		int[] stagePoints = new int[StageCount];
		int[] totalPoints = new int[15];

		for (int j = 0; j<StageCount; j++){
			int StageID = Race.races[race_counter].stages.get(j).stageId;
			stagePoints = getRidersPointsInStage(StageID);
			int riderCount = Race.races[race_counter].stages.get(j).riders.length;
			for (int k = 0; k<riderCount; k++){
				int points = stagePoints[k];
				totalPoints[k] = totalPoints[k] + points;
			}
		}

		return totalPoints;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		int race_counter = 0;
		Race current_race = Race.races[0];
		while (!found) {
			current_race = Race.races[i];
			if (current_race.race_id == raceId) {
				found = true;
				race_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		int StageCount = Race.races[race_counter].stages.size();

		int[] stagePoints = new int[StageCount];
		int[] totalPoints = new int[15];

		for (int j = 0; j<StageCount; j++){
			int StageID = Race.races[race_counter].stages.get(j).stageId;
			stagePoints = getRidersMountainPointsInStage(StageID);
			int riderCount = Race.races[race_counter].stages.get(j).riders.length;
			for (int k = 0; k<riderCount; k++){
				int points = stagePoints[k];
				totalPoints[k] = totalPoints[k] + points;
			}
		}
		return totalPoints;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		int race_counter = 0;
		Race current_race = Race.races[0];
		while (!found) {
			current_race = Race.races[i];
			if (current_race.race_id == raceId) {
				found = true;
				race_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		int StageCount = Race.races[race_counter].stages.size();

		int[] stagePoints = new int[StageCount];
		int[] totalPoints = new int[15];
		int[] riderRanks = new int[15];

		for (int j = 0; j<StageCount; j++){
			int StageID = Race.races[race_counter].stages.get(j).stageId;
			stagePoints = getRidersPointsInStage(StageID);
			int riderCount = Race.races[race_counter].stages.get(j).riders.length;
			for (int k = 0; k<riderCount; k++){
				int points = stagePoints[k];
				totalPoints[k] = totalPoints[k] + points;
				riderRanks[k] = Race.races[race_counter].stages.get(j).riders[k].id;
			}
		}

		for (int a = 0; a<14; a++){
			for (int b = 0; b<14-a-1; b++){
				if(totalPoints[b+1]>totalPoints[b]){
					int temp1 = totalPoints[b];
					totalPoints[b] = totalPoints[b+1];
					totalPoints[b+1] = temp1;
					int temp2 = riderRanks[b];
					riderRanks[b] = riderRanks[b+1];
					riderRanks[b+1] = temp1;
				}
			}

		}


		return riderRanks;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		int race_counter = 0;
		Race current_race = Race.races[0];
		while (!found) {
			current_race = Race.races[i];
			if (current_race.race_id == raceId) {
				found = true;
				race_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		int StageCount = Race.races[race_counter].stages.size();

		int[] stagePoints = new int[StageCount];
		int[] totalPoints = new int[15];

		for (int j = 0; j<StageCount; j++){
			int StageID = Race.races[race_counter].stages.get(j).stageId;
			stagePoints = getRidersPointsInStage(StageID);
			int riderCount = Race.races[race_counter].stages.get(j).riders.length;
			for (int k = 0; k<riderCount; k++){
				int points = stagePoints[k];
				totalPoints[k] = totalPoints[k] + points;
			}
		}

		for (int a = 0; a<14; a++){
			for (int b = 0; b<14-a-1; b++){
				if(totalPoints[b+1]>totalPoints[b]){
					int temp1 = totalPoints[b];
					totalPoints[b] = totalPoints[b+1];
					totalPoints[b+1] = temp1;
				}
			}

		}

		return totalPoints;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		int race_counter = 0;
		Race current_race = Race.races[0];
		while (!found) {
			current_race = Race.races[i];
			if (current_race.race_id == raceId) {
				found = true;
				race_counter = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		int StageCount = Race.races[race_counter].stages.size();

		int[] stagePoints = new int[StageCount];
		int[] totalPoints = new int[15];////////
		int[] riderRanks = new int[15];

		for (int j = 0; j<StageCount; j++){
			int StageID = Race.races[race_counter].stages.get(j).stageId;
			stagePoints = getRidersMountainPointsInStage(StageID);
			int riderCount = Race.races[race_counter].stages.get(j).riders.length;
			for (int k = 0; k<riderCount; k++){
				int points = stagePoints[k];
				totalPoints[k] = totalPoints[k] + points;
				riderRanks[k] = Race.races[race_counter].stages.get(j).riders[k].id;
			}
		}

		for (int a = 0; a<14; a++){
			for (int b = 0; b<14-a-1; b++){
				if(totalPoints[b+1]>totalPoints[b]){
					int temp1 = totalPoints[b];
					totalPoints[b] = totalPoints[b+1];
					totalPoints[b+1] = temp1;
					int temp2 = riderRanks[b];
					riderRanks[b] = riderRanks[b+1];
					riderRanks[b+1] = temp1;
				}
			}

		}

		return riderRanks;
	}

}
