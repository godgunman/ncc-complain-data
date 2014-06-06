//
//  OneChannelViewController.m
//  fakenews
//
//  Created by Golo on 14/5/28.
//  Copyright (c) 2014年 moskastudio. All rights reserved.
//

#import "OneChannelViewController.h"
#import "OneChannelCell.h"
#import "OneCategoryViewController.h"

@interface OneChannelViewController ()

@end

@implementation OneChannelViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    NSLog(@"selectedChannel:%@",self.selectedChannel);
    self.rankLable.text = [NSString stringWithFormat:@"%d",self.rank + 1];
    self.nameLable.text = [self.selectedChannel valueForKey:@"channelName"];
    self.numberLable.text = [NSString stringWithFormat:@"%@",[self.selectedChannel valueForKey:@"size"]];
    if (self.rank == 0)
    {
        self.rankLable.hidden = YES;
        self.rankBackgroundImageView.image = [UIImage imageNamed:@"FP_rank-no1-44x44"];
    }
    else if (self.rank < 5)
    {
        self.rankLable.hidden = NO;
        self.rankBackgroundImageView.image = [UIImage imageNamed:@"FP_rank-no-44x44"];
    }
    else
    {
        self.rankLable.hidden = NO;
        self.rankBackgroundImageView.image = [UIImage imageNamed:@"FP_rank-nogray-44x44"];
    }
    self.categoryArray = [self.selectedChannel valueForKey:@"category"];
    
    UITapGestureRecognizer *tapLogo = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapLogo)];
    [self.logoImageView addGestureRecognizer:tapLogo];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)tapLogo
{
    [self.navigationController popViewControllerAnimated:YES];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/


#pragma mark - UICollectionView Datasource
// 1
- (NSInteger)collectionView:(UICollectionView *)view numberOfItemsInSection:(NSInteger)section {
    return [self.categoryArray count];
}
// 2
- (NSInteger)numberOfSectionsInCollectionView: (UICollectionView *)collectionView {
    return 1;
}
// 3
- (UICollectionViewCell *)collectionView:(UICollectionView *)cv cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    OneChannelCell *cell = [cv dequeueReusableCellWithReuseIdentifier:@"oneChannelCell" forIndexPath:indexPath];
    NSMutableDictionary *eachCategory = [self.categoryArray objectAtIndex:indexPath.row];
    cell.tag = indexPath.row;
    cell.categoryNameImageView.image = [UIImage imageNamed:[self getImageNameForCategoryName:[eachCategory valueForKeyPath:@"categoryName"]]];
    cell.numberLabel.text = [NSString stringWithFormat:@"%@",[eachCategory valueForKey:@"size"]];
    return cell;
}
// 4
/*- (UICollectionReusableView *)collectionView:
 (UICollectionView *)collectionView viewForSupplementaryElementOfKind:(NSString *)kind atIndexPath:(NSIndexPath *)indexPath
 {
 return [[UICollectionReusableView alloc] init];
 }*/

#pragma mark - UICollectionViewDelegate
- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath
{
    NSLog(@"Select Item:%d",indexPath.row);
    
}
- (void)collectionView:(UICollectionView *)collectionView didDeselectItemAtIndexPath:(NSIndexPath *)indexPath {
    // TODO: Deselect item
}

- (NSString *)getImageNameForCategoryName:(NSString *)categoryName
{
    if ([categoryName isEqualToString:@"節目分級不妥"])
    {
        return @"FP_category-grade-164x72";
    }
    else if ([categoryName isEqualToString:@"妨害兒少身心"])
    {
        return @"FP_category-kid-164x72";
    }
    else if ([categoryName isEqualToString:@"妨害公序良俗"])
    {
        return @"FP_category-custom-164x72";
    }
    else if ([categoryName isEqualToString:@"針對特定頻道/節目/廣告內容、語言用字表達個人想法"])
    {
        return @"FP_category-specific-164x72";
    }
    else if ([categoryName isEqualToString:@"針對整體傳播環境、監理政策/法規或本會施政提供個人想法"])
    {
        return @"FP_category-media-164x72";
    }
    else if ([categoryName isEqualToString:@"內容不實、不公"])
    {
        return @"FP_category-content-164x72";
    }
    else if ([categoryName isEqualToString:@"違反新聞製播倫理"])
    {
        return @"FP_category-moral-164x72";
    }
    else if ([categoryName isEqualToString:@"節目與廣告未區分"])
    {
        return @"FP_category-ads-164x72";
    }
    else if ([categoryName isEqualToString:@"法規/資訊查詢"])
    {
        return @"FP_category-law-164x72";
    }
    else if ([categoryName isEqualToString:@"廣告超秒"])
    {
        return @"FP_category-toolong-164x72";
    }
    else if ([categoryName isEqualToString:@"異動未事先告知"])
    {
        return @"FP_category-change-164x72";
    }
    else if ([categoryName isEqualToString:@"違規使用插播式字幕"])
    {
        return @"FP_category-insert-164x72";
    }
    else if ([categoryName isEqualToString:@"廣告內容或排播不妥"])
    {
        return @"FP_category-notgood-164x72";
    }
    else if ([categoryName isEqualToString:@"廣電收訊、畫質或音量等技術性問題"])
    {
        return @"FP_category-tech-164x72";
    }
    else if ([categoryName isEqualToString:@"重播次數過於頻繁"])
    {
        return @"FP_category-repeat-164x72";
    }
    else
    {
        return @"FP_category-other-164x72";
    }
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(OneChannelCell *)sender
{
    if ([[segue identifier] isEqualToString:@"showOneCategory"])
    {
        OneCategoryViewController *vc = [segue destinationViewController];
        vc.selectedChannel = self.selectedChannel;
        vc.selectedCategory = [self.categoryArray objectAtIndex:sender.tag];
        vc.rank = self.rank;
    }
}
@end
