package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import cycling.Stage;


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
		if (name == null || name.isEmpty()) {
			throw new InvalidNameException("Race name cannot be null or empty");
		}
		if (!name.matches("[a-zA-Z ]+")) {
			throw new IllegalNameException("Race name can only contain letters and spaces.");
		}
		 Race race = new Race(name,description);
		 return 0;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		boolean found = false;
		String details = " ";
		int[] race_array = getRaceIds();
		int i=0;
		while (found == false){
			if (race_array[i] == raceId){
				String name = Race.races[i].race_name;
				String description = Race.races[i].description;
				found = true;
				details = name + ' ' + description;
			} else {
				i++;
			}
		}
		if (found == false){
				throw new IDNotRecognisedException();
			}
		return details;
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		boolean found = false;
		int[] race_array = getRaceIds();
		int i = 0;
		while (found == false) {
			if (race_array[i] == raceId) {
				race_array[i] = 0;
			} else {
				i++;
			}
		}
		if (found == false){
			throw new IDNotRecognisedException();
		}

	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		int stage_count;
		boolean found = false;
		int i = 0;
		Race curr = Race.races[0];
		while (found = false){
			curr = Race.races[i];
			if (curr.race_id == raceId){
				found = true;
			}else{
				i+=1;
			}
		}
		if (found == false){
			throw new IDNotRecognisedException();
		}
		stage_count = (curr.getStages()).size();
		return stage_count;
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		Race curr = Race.races[0];
		int num_stages = getNumberOfStages(raceId);
		int[] stageIds = new int[num_stages];
		while (!found) {
			curr = Race.races[i];
			if (curr.race_id == raceId) {
				found = true;
			} else {
				i += 1;
			}
		if (!found){
			throw new IDNotRecognisedException();
		}

		ArrayList<Stage>stage_list = new ArrayList<>();
		stage_list = curr.getStages();

		for (int j = 0; j <= num_stages; j++){
			Stage temp = stage_list.get(j);
			int temp_id = temp.getStageId();
			stageIds[j] = temp_id;
		}
		}
		return stageIds;
	}
	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		double stageLength;
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
		stageLength = curr.getStage_length();
		return stageLength;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		boolean found = false;
		int i = 0;
		int remove_id = 0;
		double stageLength;
		Stage curr = Stage.stages[0];
		while (!found) {
			curr = Stage.stages[i];
			if (curr.stageId == stageId) {
				found = true;
				remove_id = i;
			} else {
				i += 1;
			}
			if (!found) {
				throw new IDNotRecognisedException();
			}
			Stage.stages[remove_id] = null;
		}

	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getTeams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

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
		return null;
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
