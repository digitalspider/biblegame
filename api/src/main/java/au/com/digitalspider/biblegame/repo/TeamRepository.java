package au.com.digitalspider.biblegame.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import au.com.digitalspider.biblegame.model.Team;

@Repository
public class TeamRepository implements CrudRepository<Team, Long> {

	private Map<Long, Team> teams = new HashMap<>();

	@Override
	public long count() {
		return teams.size();
	}

	@Override
	public void delete(Long teamId) {
		teams.remove(teamId);
	}

	@Override
	public void delete(Team team) {
		teams.remove(team);
	}

	@Override
	public void delete(Iterable<? extends Team> teamList) {
		for (Team team : teamList) {
			delete(team);
		}
	}

	@Override
	public void deleteAll() {
		teams.clear();
	}

	@Override
	public boolean exists(Long teamId) {
		return teams.containsKey(teamId);
	}

	@Override
	public Iterable<Team> findAll() {
		return teams.values();
	}

	@Override
	public Iterable<Team> findAll(Iterable<Long> teamsToFind) {
		List<Team> foundTeams = new ArrayList<>();
		for (Long teamId : teamsToFind) {
			Team foundTeam = findOne(teamId);
			if (foundTeam != null) {
				foundTeams.add(foundTeam);
			}
		}
		return foundTeams;
	}

	@Override
	public Team findOne(Long teamId) {
		return teams.get(teamId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S extends Team> S save(S team) {
		return (S) teams.put(team.getId(), team);
	}

	@Override
	public <S extends Team> Iterable<S> save(Iterable<S> teams) {
		for (S team : teams) {
			save(team);
		}
		return teams;
	}

}
