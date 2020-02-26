package io.zackb.wizard.exceptions

import org.codehaus.groovy.GroovyException

class InvalidRankException extends GroovyException{
    InvalidRankException(String message){
        super(message)
    }
}
