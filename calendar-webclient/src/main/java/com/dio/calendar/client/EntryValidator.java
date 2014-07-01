package com.dio.calendar.client;

import org.apache.log4j.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import java.util.Date;

/**
 * Created by yur on 30.06.2014.
 */

@FacesValidator("entryValidator")
public class EntryValidator implements Validator {
    private static Logger logger = Logger.getLogger(EntryValidator.class);

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        logger.info("Custom validate");
        UIInput dateFrom = (UIInput) context.getViewRoot().findComponent("formEdit:editDateFrom");
        UIInput dateTo = (UIInput) context.getViewRoot().findComponent("formEdit:editDateTo");
        if (dateFrom != null && dateTo != null && dateFrom.isValid() && dateTo.isValid()) {
            if (dateFrom.getLocalValue() != null && dateTo.getLocalValue() != null) {
                Date dateDateFrom = (Date)dateFrom.getLocalValue();
                Date dateDateTo = (Date)dateTo.getLocalValue();
                if (dateDateTo.before(dateDateFrom)) {
                    throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Date From should be less or equal to Data To", null));
                }
            }
        }
    }
}
