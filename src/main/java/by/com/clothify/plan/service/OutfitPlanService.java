package by.com.clothify.plan.service;

import by.com.clothify.common.util.AuthUtils;
import by.com.clothify.plan.mapper.OutfitPlanMapper;
import by.com.clothify.plan.model.dto.OutfitPlanDto;
import by.com.clothify.plan.model.dto.SavedOutfitPlanDto;
import by.com.clothify.plan.model.entity.OutfitPlan;
import by.com.clothify.plan.model.enums.TimePeriod;
import by.com.clothify.plan.model.repository.OutfitPlanRepository;
import by.com.clothify.security.model.entity.User;
import by.com.clothify.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static by.com.clothify.common.util.DateUtils.covertToLocaleDate;
import static by.com.clothify.plan.mapper.OutfitPlanMapper.mapToMapOfOutfitPlanDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutfitPlanService {

    private final UserRepository userRepository;
    private final OutfitPlanRepository outfitPlanRepository;

    public Map<String, List<OutfitPlanDto>> getOutfitPlansByTimePeriod(TimePeriod timePeriod) {

        final String email = AuthUtils.getEmail();

        try {
            return TimePeriod.TODAY == timePeriod
                    ? getTodayOutfitPlans(email)
                    : getWeekOutfitPlans(email);

        } catch (Exception e) {
            log.error("Failed get {} outfit plans for user: {}", timePeriod, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private Map<String, List<OutfitPlanDto>> getTodayOutfitPlans(String email) {

        List<OutfitPlan> outfitPlans = outfitPlanRepository.findByPlannedDateAndEmail(LocalDate.now(), email);
        return mapToMapOfOutfitPlanDto(outfitPlans);
    }

    private Map<String, List<OutfitPlanDto>> getWeekOutfitPlans(String email) {

        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = now.with(DayOfWeek.SUNDAY);

        List<OutfitPlan> outfitPlans = outfitPlanRepository.findByWeekAndEmail(startOfWeek, endOfWeek, email);
        return mapToMapOfOutfitPlanDto(outfitPlans);
    }

    public void addOutfitPlan(SavedOutfitPlanDto savedOutfitPlanDto) {

        final String email = AuthUtils.getEmail();

        try {
            User user = userRepository.findByEmail(email).orElseThrow();
            OutfitPlan outfitPlan = OutfitPlanMapper.mapToOutfitPlan(savedOutfitPlanDto, user.getId());
            outfitPlanRepository.save(outfitPlan);

        } catch (Exception e) {
            log.error("Failed save outfit plan with outfit ID = {} for user: {}",
                    savedOutfitPlanDto.getOutfitId(), email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<OutfitPlanDto> getMonthlyOutfitPlans(Integer month, Integer year) {

        final String email = AuthUtils.getEmail();

        try {
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDate firstDay = yearMonth.atDay(1);
            LocalDate lastDay = yearMonth.atEndOfMonth();

            List<OutfitPlan> outfitPlans = outfitPlanRepository.findByDatePeriodAndEmail(firstDay, lastDay, email);
            return OutfitPlanMapper.mapToListOfOutfitPlanDto(outfitPlans);

        } catch (Exception e) {
            log.error("Failed get outfit plans for {}.{}, for user: {}", month, year, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public List<OutfitPlanDto> getOutfitPlansByDate(String date) {

        final String email = AuthUtils.getEmail();

        try {
            List<OutfitPlan> outfitPlans = outfitPlanRepository
                    .findByPlannedDateAndEmailWithFullOutfitInfo(covertToLocaleDate(date), email);
            return outfitPlans.stream()
                    .map(OutfitPlanMapper::mapToOutfitPlanDto)
                    .toList();

        } catch (Exception e) {
            log.error("Failed get outfit plans by date = {}, for user: {}", date, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public ResponseEntity<Void> deleteOutfitPlanById(Long id) {

        final String email = AuthUtils.getEmail();

        try {
            outfitPlanRepository.deleteByIdAndUser_Email(id, email);
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Failed delete outfit plan with ID = {} for user: {}", id, email);
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
