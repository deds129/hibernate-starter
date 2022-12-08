package com.nchudinov.listeners;

import com.nchudinov.entity.Audit;
import org.hibernate.event.spi.*;

//Слушает разлизчные ивенты c нашей сущностью, и создает свою сущность,
// для отслеживания исходной сущности
public class AuditTableListener implements PreDeleteEventListener, PreInsertEventListener {
	
	@Override
	public boolean onPreDelete(PreDeleteEvent event) {
		auditEntity(event, Audit.Operation.DELETE);
		return false;
	}

	private void auditEntity(AbstractPreDatabaseOperationEvent event, Audit.Operation operation) {
		if (event.getEntity().getClass() != Audit.class) {
			Audit audit = Audit.builder()
					.entityId(event.getId())
					.entityName(event.getEntityName())
					.entityContent(event.toString())
					.operation(operation)
					.build();
			event.getSession().save(audit);
		}
	}

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		auditEntity(event, Audit.Operation.INSERT);
		return false;
	}
}
