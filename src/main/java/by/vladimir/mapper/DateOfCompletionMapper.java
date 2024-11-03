package by.vladimir.mapper;

import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.DateOfCompletion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для маппинга Dto даты выполнения.
 */
@Mapper
public interface DateOfCompletionMapper {

    DateOfCompletionMapper INSTANCE = Mappers.getMapper(DateOfCompletionMapper.class);

    /**
     * Получает на вход CreateDateOfComplDto.
     * Мапит CreateDateOfComplDto в DateOfCompletion.
     * @param createDateOfComplDto - dto получаемой даты.
     * @return объект класса DateOfCompletion.
     */
    DateOfCompletion toDateOfCompl(CreateDateOfComplDto createDateOfComplDto);

    /**
     * Получает на вход DateOfCompletionDto.
     * Мапит DateOfCompletionDto в DateOfCompletion.
     * @param dateOfCompletionDto - dto получаемой даты.
     * @return объект класса DateOfCompletion.
     */
    DateOfCompletion toDateOfComplFromDto(DateOfCompletionDto dateOfCompletionDto);
}
