/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.coode.owlapi.owlxmlparser;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClassExpression;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 14-Dec-2006<br>
 * <br> */
public abstract class AbstractNaryBooleanClassExpressionElementHandler extends
        AbstractClassExpressionElementHandler {
    private Set<OWLClassExpression> operands;

    /** @param handler */
    public AbstractNaryBooleanClassExpressionElementHandler(OWLXMLParserHandler handler) {
        super(handler);
        operands = new HashSet<OWLClassExpression>();
    }

    @Override
    public void handleChild(AbstractClassExpressionElementHandler handler) {
        operands.add(handler.getOWLObject());
    }

    @Override
    protected void endClassExpressionElement() throws OWLXMLParserException {
        if (operands.size() >= 2) {
            setClassExpression(createClassExpression(operands));
        } else if (operands.size() == 1) {
            setClassExpression(operands.iterator().next());
        } else {
            throw new OWLXMLParserElementNotFoundException(
                    getLineNumber(),
                    getColumnNumber(),
                    "Found zero child elements of an "
                            + getElementName()
                            + " element. At least 2 class expression elements are required as child elements of "
                            + getElementName() + " elements");
        }
    }

    protected abstract OWLClassExpression createClassExpression(
            Set<OWLClassExpression> expressions);
}
