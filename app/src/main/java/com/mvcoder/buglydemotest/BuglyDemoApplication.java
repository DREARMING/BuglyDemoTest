package com.mvcoder.buglydemotest;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

public class BuglyDemoApplication extends TinkerApplication {

    public BuglyDemoApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.mvcoder.buglydemotest.BuglyDemoApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }


}
