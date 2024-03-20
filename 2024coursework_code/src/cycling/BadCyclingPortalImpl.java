package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.time.temporal.ChronoUnit;

import static cycling.StageType.TT;


/**
 * BadCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the CyclingPortal interface.
 * 
 * @author Diogo Pacheco
 * @version 2.0
 *
 */
public class BadCyclingPortalImpl implements CyclingPortal {

	//private Race[] races;

	@Override
	public int[] getRaceIds() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		Race race = Race.getRace(raceId);
		String name = race.race_name;
		String description = race.description;
		String details = name + ' ' + description;

		return details;
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
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
		int stage_count;
		Race curr = Race.getRace(raceId);
		stage_count = (curr.getStages()).size();
		return stage_count;
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		// TODO Auto-generated method stub

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
		stage = new Stage(type, stageName, description, length, startTime);
		int stageId = stage.getStageId();
		stage.active_status = true;
		race.stages.add(stage);
		return stageId;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {

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
		double stageLength;
		Stage curr = Stage.getStage(stageId);
		stageLength = curr.getStage_length();
		return stageLength;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		Stage removestage = Stage.getStage(stageId);
		if (removestage != null) {
			for (int i = 0; i < Stage.stages.length; i++) {
				if (Stage.stages[i] == removestage) {
					Stage.stages[i] = null;
					break;
				}
			}
		} else {
			throw new IDNotRecognisedException();
		}
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {

		int checkpoint_id = 0;
		Stage stage = Stage.getStage(stageId);
		double stage_length = getStageLength(stageId);

		if (location < 0.0 || location > stage_length){
			throw new InvalidLocationException();
		}

		if (stage.getStage_type() == TT){
			throw new InvalidStageTypeException();
		}

		if (stage.getActiveStatus() == false){
			throw new InvalidStageStateException();
		}

		Checkpoint c_climb = new Checkpoint(location, type, averageGradient, length);
		stage.checkpoints.add(c_climb);
		checkpoint_id = c_climb.getCheckpoint_id();
		return checkpoint_id;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {

		int checkpoint_id = 0;
		Stage stage = Stage.getStage(stageId);
		double stage_length = getStageLength(stageId);

		if (location < 0.0 || location > stage_length){
			throw new InvalidLocationException();
		}

		if (stage.getStage_type() == TT){
			throw new InvalidStageTypeException();
		}

		if (stage.getActiveStatus() == false){
			throw new InvalidStageStateException();
		}

		Checkpoint i_sprint = new Checkpoint(location);
		stage.checkpoints.add(i_sprint);
		checkpoint_id = i_sprint.getCheckpoint_id();

		return checkpoint_id;
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		boolean found = false;
		int i = 0;
		int remove_id = 0;
		Checkpoint curr = Checkpoint.checkpoints[0];
		while (!found) {
			curr = Checkpoint.checkpoints[i];
			if (curr.checkpoint_id == checkpointId) {
				found = true;
				remove_id = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		Checkpoint.checkpoints[remove_id] = null;
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		Stage stage = Stage.getStage(stageId);
		if (stage.active_status == false){
			stage.finish_state = true;
		} else {
			stage.finish_state = false;
			throw new InvalidStageStateException();
		}
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		int count = ((Stage.stages[stageId]).checkpoints).size();
		int[] stage_checkpoints = new int[count];
		boolean found = false;
		int i = 0;
		Stage curr = Stage.stages[0];
		while (!found) {
			curr = Stage.stages[i];
			if (curr.stageId == stageId) {
				found = true;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		for (int j = 0; j<count; j++){
			Stage temp = Stage.stages[stageId];
			Checkpoint element = temp.checkpoints.get(j);
			stage_checkpoints[j] = element.getCheckpoint_id();
		}
		return stage_checkpoints;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		int count = ((Teams.teams).length);
		Teams[] existing_teams = new Teams[count];
		String regex = "^[a-zA-Z]+$";
		Pattern pattern = Pattern.compile(regex);
		if (name == null ||!(pattern.matcher(name).matches())){
			throw new InvalidNameException();
		}
		for (int i=0; i< getTeams().length;i++){
			if (((existing_teams[i]).getTeam_name()).equals(name)){
				throw new IllegalNameException();
			}
		}
		Teams newTeam = new Teams(name,description);
		int newID = newTeam.team_id;
		return newID;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		Teams curr = Teams.teams[0];
		while (!found) {
			curr = Teams.teams[i];
			if (curr.team_id == teamId) {
				found = true;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		Teams.teams[i] = null;
	}

	@Override
	public int[] getTeams() {
		int counter = Teams.teams.length;
		int[] team_ids = new int[counter];
		for (int i =0; i<counter;i++){
			Teams curr = Teams.teams[i];
			team_ids[i] = curr.team_id;
		}
		return team_ids;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		Teams curr = Teams.teams[0];
		while (!found) {
			curr = Teams.teams[i];
			if (curr.team_id == teamId) {
				found = true;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		int riders = curr.getTeam_riders().size();
		int[] team_riders = new int[riders];
		for (int j =0; j<riders; j++){
			team_riders[j] = curr.getTeam_riders().get(j).getId();
		}

		return team_riders;
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		boolean found = false;
		int i = 0;
		Teams curr = Teams.teams[0];
		while (!found) {
			curr = Teams.teams[i];
			if (curr.team_id == teamID) {
				found = true;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		Riders rider = new Riders(teamID,name,yearOfBirth);
		curr.team_riders.add(rider);
		int id = rider.getId();

		return id;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		Riders curr = Riders.riders[0];
		while (!found) {
			curr = Riders.riders[i];
			if (curr.id == riderId) {
				found = true;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
		}
		Riders.riders[i] = null;
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
		i = 0;
		while (!found) {
			current_stage = Stage.stages[i];
			if (current_stage.stageId == stageId) {
				found = true;
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
		return times;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

}
