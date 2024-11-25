package by.audit.auditservice.impl;


import by.audit.entity.Audit;
import by.audit.auditservice.AuditService;
import by.audit.dao.AuditDao;
import by.audit.dto.AuditDto;
import by.audit.mapper.AuditMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final AuditDao auditDao;
    private final AuditMapper auditMapper;
    @Override
    public void saveAudit(AuditDto auditDto){
        Audit audit = auditMapper.toAudit(auditDto);
        auditDao.save(audit);
    }
}