//========================================================================
//Copyright 2007-2008 David Yu dyuproject@gmail.com
//------------------------------------------------------------------------
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at 
//http://www.apache.org/licenses/LICENSE-2.0
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
//========================================================================

package com.dyuproject.protostuff.json;

import java.io.IOException;

import com.dyuproject.protostuff.model.Model;
import com.dyuproject.protostuff.model.ModelMeta;

/**
 * @author David Yu
 * @created Oct 2, 2009
 */

public class NumberedProtobufConvertor extends DefaultProtobufConvertor
{
    
    public static final String PREFIX = System.getProperty("json.numeric_prefix", "_");

    public NumberedProtobufConvertor(ModelMeta modelMeta, ProtobufConvertorFactory<DefaultProtobufConvertor> factory)
    {
        super(modelMeta,factory);
    }
    
    protected Field getField(String name, Model<Field> model) throws IOException
    {        
        if(name.startsWith(PREFIX) && name.length()>PREFIX.length())
            return model.getProperty(Integer.parseInt(name.substring(PREFIX.length())));        
        
        throw new IOException("unknown field: " + name + " on message: " + 
                _model.getModelMeta().getMessageClass());
    }
    
    protected String getFieldName(Field field)
    {
        return PREFIX + field.getPropertyMeta().getNumber();
    }
    
    public static class Factory extends DefaultProtobufConvertor.Factory
    {

        public Factory(Class<?>[] moduleClasses)
        {
            super(moduleClasses);
        }
        
        public Factory(ModelMeta.Factory factory, Class<?>[] moduleClasses)
        {
            super(factory, moduleClasses);
        }
        
        protected NumberedProtobufConvertor newConvertor(ModelMeta modelMeta)
        {
            return new NumberedProtobufConvertor(modelMeta, this);
        }
        
    }

}