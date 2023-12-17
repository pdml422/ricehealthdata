package vn.edu.usth.service.data;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import vn.edu.usth.exception.DataNotFoundException;
import vn.edu.usth.payload.SearchStatistical;
import vn.edu.usth.model.Statistical;
import vn.edu.usth.repository.StatisticalRepository;

import java.util.List;

@ApplicationScoped
public class StatisticalService {
    private final StatisticalRepository statisticalRepository;
    @Inject
    public StatisticalService(StatisticalRepository statisticalRepository) {
        this.statisticalRepository = statisticalRepository;
    }

    public List<Statistical> search(SearchStatistical searchStatistical) {
        return statisticalRepository.search(searchStatistical);
    }

    public Statistical getDataById(int id) throws DataNotFoundException {
        Statistical statistical = statisticalRepository.findById((long) id);
        if (statistical == null) {
            throw new DataNotFoundException("Not on Database");
        }
        return statistical;
    }

    @Transactional
    public Statistical addData(Statistical statistical) {
        statisticalRepository.persist(statistical);
        return statistical;
    }

    @Transactional
    public Statistical updateData(int id, Statistical statistical) throws DataNotFoundException {
        Statistical stat = getDataById(id);

        stat.setReplicate(statistical.getReplicate());
        stat.setSubReplicate(statistical.getSubReplicate());
        stat.setDate(statistical.getDate());
        stat.setLongitude(statistical.getLongitude());
        stat.setLatitude(statistical.getLatitude());
        stat.setChlorophyll(statistical.getChlorophyll());
        stat.setPConc(statistical.getPConc());
        stat.setKConc(statistical.getKConc());
        stat.setNConc(statistical.getNConc());
        stat.setWetWeight(statistical.getWetWeight());
        stat.setDriedWeight(statistical.getDriedWeight());
        stat.setMoiture(statistical.getMoiture());
        stat.setDigesion(statistical.getDigesion());

        statisticalRepository.persist(stat);
        return stat;
    }

    @Transactional
    public void deleteStat(int id) throws DataNotFoundException {
        statisticalRepository.delete(getDataById(id));
    }

}