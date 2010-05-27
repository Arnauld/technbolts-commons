/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.technbolts.dto.domain.common;

import org.technbolts.dto.configuration.Version;
import org.technbolts.dto.configuration.annotation.Alias;
import org.technbolts.dto.configuration.annotation.Since;

/**
 * Response of a request that not need 
 * @author <a href="stephane@technbolts.com">Stéphane Kermabon</a>
 */
public class SuccessDTO {

    @Since(Version.V1)
    @Alias(value="success", since=Version.V1)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
