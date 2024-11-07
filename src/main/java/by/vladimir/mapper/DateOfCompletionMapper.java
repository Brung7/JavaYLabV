package by.vladimir.mapper;

import by.vladimir.dto.CreateDateOfComplDto;
import by.vladimir.dto.DateOfCompletionDto;
import by.vladimir.entity.DateOfCompletion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для маппинга Dto даты выполнения.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DateOfCompletionMapper {


    /**
     * Получает на вход CreateDateOfComplDto.
     * Мапит CreateDateOfComplDto в DateOfCompletion.
     * @param createDateOfComplDto - dto получаемой даты.
     * @return объект класса DateOfCompletion.
     */
    @Mapping(source = "id",target = "id")
    @Mapping(source = "habitId",target = "habitId")
    @Mapping(source = "date",target = "date")
    DateOfCompletion toDateOfCompl(CreateDateOfComplDto createDateOfComplDto);

    /**
     * Получает на вход DateOfCompletionDto.
     * Мапит DateOfCompletionDto в DateOfCompletion.
     * @param dateOfCompletionDto - dto получаемой даты.
     * @return объект класса DateOfCompletion.
     */

    @Mapping(source = "id",target = "id")
    @Mapping(source = "date",target = "date")
    DateOfCompletion toDateOfComplFromDto(DateOfCompletionDto dateOfCompletionDto);
}