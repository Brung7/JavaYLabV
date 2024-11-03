package by.vladimir.service;

import by.vladimir.dao.AuditDao;
import by.vladimir.dto.AuditDto;
import by.vladimir.entity.Audit;
import by.vladimir.mapper.AuditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private AuditDao auditDao;
    private AuditMapper auditMapper;

    @Autowired
    public AuditService(AuditDao auditDao, AuditMapper auditMapper) {
        this.auditDao = auditDao;
        this.auditMapper = auditMapper;
    }

    public void saveAudit(AuditDto auditDto){
        Audit audit = auditMapper.toAudit(auditDto);
        auditDao.save(audit);
    }

}
