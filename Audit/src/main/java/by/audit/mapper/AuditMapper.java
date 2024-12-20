package by.audit.mapper;


import by.audit.entity.Audit;
import by.audit.dto.AuditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Интерфейс для маппинга Dto аудита.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuditMapper {

    /**
     * Получает на вход auditDto.
     * Маппит auditDto в Audit.
     * @param auditDto - Dto аудита.
     * @return объект класса Audit
     */
    @Mapping(source = "username", target = "username")
    @Mapping(source = "methodName", target = "methodName")
    @Mapping(source = "time", target = "time")
    @Mapping(source = "actionResult", target = "actionResult")
    Audit toAudit(AuditDto auditDto);

}