//
//  rotatingItem.h
//  queen
//
//  Created by Golo on 13/10/1.
//  Copyright (c) 2013年 moskastudio. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>

@interface rotatingItem : UIImageView
@property CABasicAnimation* rotationAnimation;
- (void)startRotating;
- (void)stopRotating;

@end
