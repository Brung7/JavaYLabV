package by.audit.auditservice;


import by.audit.dto.AuditDto;

public interface AuditService {

    void saveAudit(AuditDto auditDto);
}